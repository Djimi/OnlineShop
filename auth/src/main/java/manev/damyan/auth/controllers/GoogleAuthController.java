package manev.damyan.auth.controllers;

import lombok.AllArgsConstructor;
import manev.damyan.auth.dto.CreateUserDTO;
import manev.damyan.auth.dto.profiles.ProfileDTOResponse;
import manev.damyan.auth.services.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@AllArgsConstructor
public class GoogleAuthController {

    private UserService userService;

    private static final String GOOGLE_PUBLIC_KEYS_URL = "https://www.googleapis.com/oauth2/v3/certs";

    @GetMapping("/oauth-google")
    public String grantCode(@RequestParam("code") String code, @RequestParam("scope") String scope,
            @RequestParam("authuser") String authUser, @RequestParam("prompt") String prompt) {

        GoogleAccessTokenResponse oauthAccessTokenGoogle = getOauthAccessTokenGoogle(code);
        NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder jwkSetUriJwtDecoderBuilder = NimbusJwtDecoder.withJwkSetUri(
                GOOGLE_PUBLIC_KEYS_URL);

        Jwt decode = jwkSetUriJwtDecoderBuilder.build().decode(oauthAccessTokenGoogle.getIdToken());
        Map<String, Object> claims = decode.getClaims();
        ProfileDTOResponse userProfile = userService.createUserProfile(
                new CreateUserDTO(claims.get("email").toString(), "", "basic",
                        "This is automatically created user info after login via Google. User is with name: " +
                                claims.get("name")));


        return "User created ^_^ . Its profile id is: " + userProfile.getId();
    }

    private GoogleAccessTokenResponse getOauthAccessTokenGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:10000/login-with-google");
//        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        GoogleAccessTokenResponse response = restTemplate.postForObject(url, requestEntity,
                GoogleAccessTokenResponse.class);

        return response;
    }

}

package manev.damyan.auth.http;

import manev.damyan.auth.dto.profiles.ProfileDTORequest;
import manev.damyan.auth.dto.profiles.ProfileDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profileClient", url = "http://${services.account-endpoint.hostname}:${services.account-endpoint.port}", path = "/profiles")
public interface ProfileClient {

    @PostMapping
    public ResponseEntity<ProfileDTOResponse> createUserProfile(@RequestBody ProfileDTORequest dtoRequest);

}

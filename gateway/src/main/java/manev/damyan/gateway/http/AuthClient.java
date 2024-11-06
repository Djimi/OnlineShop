package manev.damyan.gateway.http;

import feign.Response;
import manev.damyan.gateway.dto.UserBasicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "profileClient", url = "http://${services.auth-service.host}:${services.auth-service.port}", path = "/user-basic-auth")
public interface AuthClient {

    @PostMapping
    Response login(@RequestHeader("Authorization") String authorizationHeader);

}
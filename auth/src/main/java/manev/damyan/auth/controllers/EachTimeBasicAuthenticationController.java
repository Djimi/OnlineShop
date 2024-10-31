package manev.damyan.auth.controllers;

import lombok.AllArgsConstructor;
import manev.damyan.auth.dto.UserBasicDTO;
import manev.damyan.auth.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class EachTimeBasicAuthenticationController {

    private UserService userService;

    @PostMapping("/user-basic-auth")
    public ResponseEntity<UserBasicDTO> getMyUserInfo(Principal principal) {

        try {
            return ResponseEntity.ok(userService.getBasicUserInfo(principal.getName()));
        } catch (Exception e) {
            System.out.println("Exception occurred while trying to get basic info for user!");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}

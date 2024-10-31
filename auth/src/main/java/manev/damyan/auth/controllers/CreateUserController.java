package manev.damyan.auth.controllers;

import lombok.AllArgsConstructor;
import manev.damyan.auth.dto.CreateUserDTO;
import manev.damyan.auth.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateUserController {

    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<CreatedUserResponseDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {

        try {
            return ResponseEntity.ok(userService.createUser(createUserDTO));
        } catch (Exception e) {
            System.out.println("Exception occurred");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}

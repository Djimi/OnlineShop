package manev.damyan.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {


    private String username;

    private String password;

    private String type;

    private String userDescription;
}

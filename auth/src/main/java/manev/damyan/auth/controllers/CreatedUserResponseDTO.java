package manev.damyan.auth.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUserResponseDTO {

    private String username;
    private String type;
    private String description;
}

package manev.damyan.auth.dto.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTOResponse {
    private String id;
    private String username;
    private String description;
}

package manev.damyan.accounts.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProfileDTO {

    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    @NotEmpty
    @Length(min = 3)
    private String username;

    @NotNull
    @NotEmpty
    private String description;
}

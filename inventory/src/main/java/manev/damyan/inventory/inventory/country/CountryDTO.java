package manev.damyan.inventory.inventory.country;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CountryDTO {

    @CountryNameConstraint
    private String iso;

    @NotEmpty(message = "iso of the country should not be empty!")
    @Pattern(regexp = "(^[A-Z].*$)", message = "Country name should start with uppercase letter!")
    private String name;
}

package manev.damyan.inventory.inventory.country;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CountryDTO {
    private String iso;

    private String name;

}

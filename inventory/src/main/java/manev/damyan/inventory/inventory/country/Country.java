package manev.damyan.inventory.inventory.country;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Table(name = "country")
@Entity
@Data
public class Country {

    @Id
    @Column(name = "iso")
    @Length(min = 3, max = 3)
    private String iso;

    @Column(name = "name")
    private String name;

}

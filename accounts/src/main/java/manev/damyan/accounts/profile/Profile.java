package manev.damyan.accounts.profile;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("profile")
@Data
public class Profile {

    @Id
    private String id;
    private String loginName;
    private String userDescription;
}

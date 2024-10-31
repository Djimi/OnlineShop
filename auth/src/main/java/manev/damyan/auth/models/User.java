package manev.damyan.auth.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String hashedPassword;

    private String type; // basic, moderator, admin

    public User(String username, String hashedPassword, String type) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.type = type;
    }
}

package manev.damyan.purchase.config;

import lombok.Data;
import org.springframework.data.jpa.repository.EntityGraph;

@Data
public class ServiceBaseConfig {
    private String host;
    private int port;
}

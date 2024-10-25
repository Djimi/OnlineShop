package manev.damyan.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("services")
@Data
public class ServicesEndpoints {
    private Endpoint inventoryService;
}

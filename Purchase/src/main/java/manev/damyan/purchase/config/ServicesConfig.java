package manev.damyan.purchase.config;

import lombok.Data;
import manev.damyan.purchase.inventory.InventoryService;
import manev.damyan.purchase.inventory.InventoryServiceConfig;
import manev.damyan.purchase.profile.ProfileServiceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "services-config")
public class ServicesConfig {

    private InventoryServiceConfig inventory;
    private ProfileServiceConfig profile;

}

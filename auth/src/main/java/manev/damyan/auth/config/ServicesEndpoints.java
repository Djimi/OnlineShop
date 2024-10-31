package manev.damyan.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("services")
public class ServicesEndpoints {
    public ServiceEndpoint accountEndpoint;
}

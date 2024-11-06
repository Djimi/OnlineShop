package manev.damyan.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RouterConfig {

    private ServicesEndpoints servicesEndpoints;

    @Bean
    public RouteLocator myRouter(RouteLocatorBuilder builder) {
        Endpoint inventoryServiceEndpoint = servicesEndpoints.getInventoryService();
        Endpoint authServiceEndpoint = servicesEndpoints.getAuthService();
        return builder.routes()
                .route(p -> p
                        .path("/items")
                        .filters(f -> f.addRequestHeader("Source", "ApiGateway"))
                        .uri(String.format("http://%1$s:%2$s", inventoryServiceEndpoint.getHost(),
                                inventoryServiceEndpoint.getPort())))
                .route(p -> p
                        .path("/user")
                        .filters(f -> f.addRequestHeader("Source", "ApiGateway"))
                        .uri(String.format("http://%1$s:%2$s", authServiceEndpoint.getHost(),
                                authServiceEndpoint.getPort())))
                .route(p -> p.path("/login-with-google")
                        .filters(f -> f.addRequestHeader("Source", "ApiGatew")
                                .rewritePath("/login-with-google", "/oauth-google"))
                        .uri(String.format("http://%1$s:%2$s", authServiceEndpoint.getHost(),
                                authServiceEndpoint.getPort())))
                .build();
    }
}

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
        return builder.routes()
                .route(p -> p
                        .path("/items")
                        .filters(f -> f.addRequestHeader("Source", "ApiGateway"))
                        .uri(String.format("http://%1$s:%2$s", inventoryServiceEndpoint.getHost(), inventoryServiceEndpoint.getPort())))
                .build();
    }
}

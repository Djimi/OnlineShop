package manev.damyan.gateway.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import manev.damyan.gateway.dto.UserBasicDTO;
import manev.damyan.gateway.http.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class BasicAuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthClient authClient;

    private final ObjectMapper objectMapper;

    @Autowired
    public BasicAuthGlobalFilter(@Lazy AuthClient authClient, ObjectMapper mapper) {
        this.authClient = authClient;
        this.objectMapper = mapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String googleAuthURL = "/login-with-google";
        if (googleAuthURL.equals(exchange.getRequest().getPath().toString())) {
            return chain.filter(exchange);
        }

        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
//            chain.filter(exchange);
        }

        Response login = authClient.login(authorizationHeader);

        if (login.status() == 200) {

            try {
                UserBasicDTO responseBody = objectMapper.readValue(login.body().asInputStream(), UserBasicDTO.class);
                if (responseBody.getUsername() != null) {
                    System.out.println("Non null username hence the request is authenticated");
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

    }

    @Override
    public int getOrder() {
        return -1; // Set order if you have multiple global filters
    }
}

package manev.damyan.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Explicitly disable CSRF (already covered, just keeping it explicit here)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user-basic-auth").authenticated() // Protect only this specific URL
                        .anyRequest().permitAll() // All other URLs are accessible without authentication
                )
                .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic Auth only for authenticated requests
                .build();
    }

}

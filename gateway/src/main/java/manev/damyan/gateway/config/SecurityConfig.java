package manev.damyan.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable()) // Explicitly disable CSRF (already covered, just keeping it explicit here)
//                .authorizeHttpRequests(requests -> requests
////                        .requestMatchers("/user-basic-auth").authenticated() // Protect only this specific URL
//                        .anyRequest().authenticated() // All other URLs are accessible without authentication
//                )
//                .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic Auth only for authenticated requests
//                .build();
//    }

}

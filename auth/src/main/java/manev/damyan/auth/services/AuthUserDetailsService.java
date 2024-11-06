package manev.damyan.auth.services;

import lombok.AllArgsConstructor;
import manev.damyan.auth.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s cannot be found in database!", username));
        }

        User userExistent = user.get();

        // mapping our user to Spring one
        return new org.springframework.security.core.userdetails.User(userExistent.getUsername(),
                userExistent.getHashedPassword(), List.of(new SimpleGrantedAuthority(userExistent.getType())));
    }
}

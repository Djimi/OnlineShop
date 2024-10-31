package manev.damyan.auth.services;

import lombok.AllArgsConstructor;
import manev.damyan.auth.controllers.CreatedUserResponseDTO;
import manev.damyan.auth.dto.CreateUserDTO;
import manev.damyan.auth.dto.UserBasicDTO;
import manev.damyan.auth.dto.profiles.ProfileDTORequest;
import manev.damyan.auth.dto.profiles.ProfileDTOResponse;
import manev.damyan.auth.exceptions.UserCreationFailure;
import manev.damyan.auth.exceptions.UserNotFoundException;
import manev.damyan.auth.http.ProfileClient;
import manev.damyan.auth.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private ProfileClient profileClient;

    private PasswordEncoder passwordEncoder;

    public CreatedUserResponseDTO createUser(CreateUserDTO createUserDTO) {

        User entity = convertDtoToModel(createUserDTO);
        userRepository.save(entity);
        ProfileDTOResponse userProfile = createUserProfile(createUserDTO);
        return convertEntityToDTO(entity, userProfile);
    }
    public ProfileDTOResponse createUserProfile(CreateUserDTO createUserDTO) {
        ResponseEntity<ProfileDTOResponse> userCreationResponse = profileClient.createUserProfile(
                new ProfileDTORequest(createUserDTO.getUsername(), createUserDTO.getUserDescription()));

        if (userCreationResponse.getStatusCode().is2xxSuccessful()) {
            return userCreationResponse.getBody();

        } else {
            throw new UserCreationFailure("Failure when trying to create profile in Profile service!");
        }
    }

    public UserBasicDTO getBasicUserInfo(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User is not found with username: %s", username));
        }

        User userEntity = user.get();
        return new UserBasicDTO(userEntity.getId(), userEntity.getUsername(), userEntity.getType());

    }

    private User convertDtoToModel(CreateUserDTO createUserDTO) {
        return new User(createUserDTO.getUsername(), passwordEncoder.encode(createUserDTO.getPassword()),
                createUserDTO.getType());
    }

    private CreatedUserResponseDTO convertEntityToDTO(User entity, ProfileDTOResponse userProfile) {
        return new CreatedUserResponseDTO(entity.getUsername(), entity.getType(), userProfile.getDescription());
    }
}

package manev.damyan.accounts.profile;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;

    private ProfileMapper mapper;

    public Mono<ProfileDTO> getProfileById(String id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return profileRepository.findById(id).map(mapper::convertEntityToDTO);
    }

    public Mono<ProfileDTO> getProfileByUsername(String username) {
        return profileRepository.findProfileByUsername(username).map(mapper::convertEntityToDTO);
    }

    public Flux<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll().map(mapper::convertEntityToDTO);
    }

    public Mono<Void> deleteProfileById(String id) {
        return profileRepository.deleteById(id);
    }

    public Mono<ProfileDTO> createProfile(NewProfileDTO dto) {
        return profileRepository.save(mapper.convertNewProfileDTOToEntity(dto)).map(mapper::convertEntityToDTO);
    }

    public Mono<ProfileDTO> updateProfile(ProfileDTO dto) {
        return profileRepository.save(mapper.convertDTOToEntity(dto)).map(mapper::convertEntityToDTO);
    }
}

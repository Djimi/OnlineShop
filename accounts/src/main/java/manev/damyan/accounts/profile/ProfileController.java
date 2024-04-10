package manev.damyan.accounts.profile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProfileDTO>> getProfile(@PathVariable("id") String userId) {
        return profileService.getProfileById(userId)
                .map(profile -> ResponseEntity.ok(profile))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mono<ProfileDTO>> createProfile(@RequestBody @Validated NewProfileDTO dto) {
        return ResponseEntity.ok().body(profileService.createProfile(dto));
    }

    @GetMapping
    public ResponseEntity<Flux<ProfileDTO>> getProfiles() {
        return ResponseEntity.ok().body(profileService.getAllProfiles());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProfile(@PathVariable("id") String userId) {
        return profileService.deleteProfileById(userId).thenReturn(ResponseEntity.ok().build());
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProfileDTO>> createProfile(@RequestBody @Validated ProfileDTO dto,
            @NotNull @PathVariable("id") String userId) {
        if (!userId.equals(dto.getId())) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return profileService.updateProfile(dto).map(result -> ResponseEntity.ok(result));
    }

}

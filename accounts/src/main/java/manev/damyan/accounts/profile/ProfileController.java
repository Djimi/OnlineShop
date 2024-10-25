package manev.damyan.accounts.profile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
@Slf4j
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProfileDTO>> getProfile(@PathVariable("id") String userId,  ServerWebExchange exchange) {
        log.info("Requesting account with uuid: " + userId);
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

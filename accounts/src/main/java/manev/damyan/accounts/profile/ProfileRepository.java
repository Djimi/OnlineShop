package manev.damyan.accounts.profile;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

    @Query("{username:'?0'}")
    Mono<Profile> findProfileByUsername(String username);
}

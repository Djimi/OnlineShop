package manev.damyan.purchase.profile;

import manev.damyan.purchase.config.ServicesConfig;
import manev.damyan.purchase.purchases.IncorrectOrderDataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProfileService {

    private WebClient profileClient;

    public ProfileService(WebClient.Builder builder, ServicesConfig config) {
        this.profileClient = builder.baseUrl(String.format("http://%s:%s", config.getProfile().getHost(),
                config.getProfile().getPort())).build();
    }

    public Mono<ProfileDTO> getProfile(String id) {

//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        WebClient.ResponseSpec responseSpec = profileClient
                .get()
                .uri(String.format("/profiles/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        return responseSpec
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new IncorrectOrderDataException(String.format("Profile with id [%s] doesn't exist", id))))

                .toEntity(ProfileDTO.class)
                .doOnSuccess(responseEntity -> {
                    if (responseEntity != null) {
                        HttpHeaders headers = responseEntity.getHeaders();
                    }
                })
                .map(ResponseEntity::getBody);
    }

}

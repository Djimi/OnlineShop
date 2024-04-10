package manev.damyan.purchase.inventory;

import manev.damyan.purchase.config.ServicesConfig;
import manev.damyan.purchase.purchases.IncorrectOrderDataException;
import manev.damyan.purchase.purchases.PurchaseItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryService {

    private WebClient inventoryClient;

    public InventoryService(WebClient.Builder builder, ServicesConfig config) {
        this.inventoryClient = builder.baseUrl(String.format("http://%s:%s", config.getInventory().getHost(),
                config.getInventory().getPort())).build();
    }

    public Mono<PurchaseItem> reduceInventory(PurchaseItem item) {

        WebClient.ResponseSpec responseSpec = inventoryClient
                .post()
                .uri(String.format("/inventories/item/%s/decrease", item.getItemId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new PurchaseItem(item.getItemId(), item.getAmount())), PurchaseItem.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        return responseSpec
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new IncorrectOrderDataException(String.format("Item with id [%s] doesn't exist", item.getItemId()))))
                .onStatus(HttpStatus.UNPROCESSABLE_ENTITY::equals,
                        response -> Mono.error(new IncorrectOrderDataException( String.format("Insufficient amount [%s] for item with id [%s]", item.getAmount(),item.getItemId()))))
                .toEntity(PurchaseItem.class)
                .map(ResponseEntity::getBody);
    }
}

package manev.damyan.purchase.purchases;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manev.damyan.purchase.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchases")
@AllArgsConstructor
@Slf4j
public class PurchaseController {

    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseDTO> createPurchase(@RequestBody CreatePurchaseDTO createPurchaseDTO) {
        return ResponseEntity.ok().body(purchaseService.createPurchase(createPurchaseDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<PurchaseDTO> getPurchase(@PathVariable("id") String id) {
        log.info("Requesting purchase with id: " + id);
        return purchaseService.getPurchase(id)
                .map(x -> ResponseEntity.ok(x))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        log.info("All purchase endpoint hit!");
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable("id") String id, @RequestBody PurchaseDTO purchaseDTO) {

        ObjectMapper mapper = new ObjectMapper();

        if (purchaseDTO.getPurchase().getPurchaseItems() != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(UUID.randomUUID().toString(),
                    "Updating inventory is now allowed on already created purchase", null));
        }

        if (!id.equals(purchaseDTO.getId())) {
            return ResponseEntity.badRequest().body(new ErrorResponse(UUID.randomUUID().toString(),
                    "Id in the path and in the body should match!", null));
        }

        return ResponseEntity.ok(purchaseService.updatePurchase(purchaseDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable("id") String id) {
        return purchaseService.deletePurchase(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

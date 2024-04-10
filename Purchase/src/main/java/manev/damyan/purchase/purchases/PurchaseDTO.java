package manev.damyan.purchase.purchases;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class PurchaseDTO {

    private String id;

    @JsonUnwrapped
    private CreatePurchaseDTO purchase;
}

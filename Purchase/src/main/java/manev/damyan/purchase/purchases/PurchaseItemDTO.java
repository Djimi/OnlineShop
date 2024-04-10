package manev.damyan.purchase.purchases;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseItemDTO {
    private int itemId;

    private int amount;
}

package manev.damyan.inventory.inventory.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryItemDTO {

    private Long itemId;

    private int amount;
}

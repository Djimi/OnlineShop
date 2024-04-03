package manev.damyan.inventory.inventory.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InventoryDTO {

    @JsonProperty("item_id")
    private Long itemId;

    @JsonProperty("warehouse_id")
    private Long warehouseId;

    private int amount;
}

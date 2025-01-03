package manev.damyan.inventory.inventory.analytics;

import lombok.Data;
import manev.damyan.inventory.inventory.items.ItemDTO;

@Data
public class ItemReportingDTO {
    private final String type;

    private final ItemDTO oldDTO;

    private final ItemDTO newDTO;
}

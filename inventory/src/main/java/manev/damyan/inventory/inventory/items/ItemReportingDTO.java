package manev.damyan.inventory.inventory.items;

import lombok.Data;

@Data
public class ItemReportingDTO {
    private final String type;

    private final ItemDTO oldDTO;

    private final ItemDTO newDTO;
}

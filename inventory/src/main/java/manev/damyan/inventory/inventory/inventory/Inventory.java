package manev.damyan.inventory.inventory.inventory;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import manev.damyan.inventory.inventory.items.Item;
import manev.damyan.inventory.inventory.warehouse.Warehouse;

import java.util.Objects;

@Entity
@Table(name = "inventory")
@Getter
@Setter
public class Inventory {

    @EmbeddedId
    private InventoryId id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @MapsId("itemId")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @MapsId("warehouseId")
    private Warehouse warehouse;

    @Column(name = "amount")
    @Min(0)
    private int amount;

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Inventory that = (Inventory) o;
        return Objects.equals(item, that.item) &&
                Objects.equals(warehouse, that.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, warehouse);
    }
}

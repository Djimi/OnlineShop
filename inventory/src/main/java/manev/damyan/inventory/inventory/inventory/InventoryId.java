package manev.damyan.inventory.inventory.inventory;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryId implements Serializable {

    private Long warehouseId;

    private Long itemId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        InventoryId that = (InventoryId) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, warehouseId);
    }
}

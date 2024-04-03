package manev.damyan.inventory.inventory.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {

    List<Inventory> findByWarehouseId(Long warehouseId);

    List<Inventory> findByItemId(Long itemId);
}

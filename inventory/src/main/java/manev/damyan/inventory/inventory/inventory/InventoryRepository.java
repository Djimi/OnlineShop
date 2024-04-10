package manev.damyan.inventory.inventory.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {

    List<Inventory> findByIdWarehouseId(Long warehouseId);

    List<Inventory> findByIdItemId(Long itemId);
}

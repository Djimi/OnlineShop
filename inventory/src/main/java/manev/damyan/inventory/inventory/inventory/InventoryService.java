package manev.damyan.inventory.inventory.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryDTO addInventory(Long warehouseId, Long itemId, AddInventoryDTO dto) {

        Optional<Inventory> currentInventory = inventoryRepository.findById(new InventoryId(warehouseId, itemId));

        Inventory inventory;

        if (currentInventory.isPresent()) {
            inventory = currentInventory.get();
            inventory.setAmount(inventory.getAmount() + dto.getAmount());
        } else {
            inventory = inventoryMapper.convertAddInventoryToEntity(warehouseId, itemId, dto);
        }

        return inventoryMapper.convertToDTO(inventoryRepository.save(inventory));
    }

    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream().map(inventoryMapper::convertToDTO).collect(Collectors.toList());
    }

    public Optional<InventoryDTO> getInventory(Long warehouseId, Long itemId) {
        Optional<Inventory> inventory = inventoryRepository.findById(new InventoryId(warehouseId, itemId));

        return inventory.map(inventoryMapper::convertToDTO);
    }

    public List<InventoryDTO> getAllInventoriesForWarehouse(Long warehouseId) {
        return inventoryRepository.findByWarehouseId(warehouseId).stream().map(inventoryMapper::convertToDTO).collect(
                Collectors.toList());
    }

    public List<InventoryDTO> getAllInventoriesForItem(Long itemId) {
        return inventoryRepository.findByWarehouseId(itemId).stream().map(inventoryMapper::convertToDTO).collect(
                Collectors.toList());
    }
}

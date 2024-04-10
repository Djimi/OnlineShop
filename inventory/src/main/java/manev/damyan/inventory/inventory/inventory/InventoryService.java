package manev.damyan.inventory.inventory.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryDTO addInventory(Long warehouseId, Long itemId, UpdateInventoryDTO dto) {

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

    @Transactional
    public InventoryItemDTO removeInventory(Long itemId, UpdateInventoryDTO dto) {
        List<Inventory> inventories = inventoryRepository.findByIdItemId(itemId);

        int amountNeeded = dto.getAmount();
        int needToTake = amountNeeded;

        for (int i = 0; i < inventories.size() && needToTake > 0; ++i) {
            Inventory inventory = inventories.get(i);
            int currentAmount = inventory.getAmount();
            int takenAmount = Math.min(amountNeeded, currentAmount);
            inventory.setAmount(currentAmount - takenAmount);
            needToTake -= takenAmount;
        }

        if (needToTake != 0) {
            String message = String.format(
                    "Fail to take the respective amount: [%s] of item with id [%s], because of insufficient resources. Need more: [%s] to complete the request",
                    amountNeeded, itemId, needToTake);
            throw new InsufficientResourceException(message, null, itemId, amountNeeded, amountNeeded - needToTake);
        }

        return new InventoryItemDTO(itemId, amountNeeded);
    }

    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream().map(inventoryMapper::convertToDTO).collect(Collectors.toList());
    }

    public Optional<InventoryDTO> getInventory(Long warehouseId, Long itemId) {
        Optional<Inventory> inventory = inventoryRepository.findById(new InventoryId(warehouseId, itemId));

        return inventory.map(inventoryMapper::convertToDTO);
    }

    public List<InventoryDTO> getAllInventoriesForWarehouse(Long warehouseId) {
        return inventoryRepository.findByIdWarehouseId(warehouseId).stream().map(inventoryMapper::convertToDTO).collect(
                Collectors.toList());
    }

    public List<InventoryDTO> getAllInventoriesForItem(Long itemId) {
        return inventoryRepository.findByIdItemId(itemId).stream().map(inventoryMapper::convertToDTO).collect(
                Collectors.toList());
    }
}

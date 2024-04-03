package manev.damyan.inventory.inventory.inventory;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import manev.damyan.inventory.inventory.items.Item;
import manev.damyan.inventory.inventory.items.ItemRepository;
import manev.damyan.inventory.inventory.warehouse.Warehouse;
import manev.damyan.inventory.inventory.warehouse.WarehouseRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//@AllArgsConstructor
public abstract class InventoryMapper {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Mapping(source = "id.itemId", target = "itemId")
    @Mapping(source = "id.warehouseId", target = "warehouseId")
    public abstract InventoryDTO convertToDTO(Inventory entity);

    @Mapping(source = "warehouseId", qualifiedByName = "getWarehouseReference", target = "warehouse")
    @Mapping(source = "itemId", qualifiedByName = "getItemReference", target = "item")
    @Mapping(source = "dto", target = ".")
    @Mapping(source = "warehouseId", target = "id.warehouseId")
    @Mapping(source = "itemId", target = "id.itemId")
    public abstract Inventory convertAddInventoryToEntity(Long warehouseId, Long itemId, AddInventoryDTO dto);

    @Named("getItemReference")
    protected Item getItemReference(Long itemId) {
        return itemRepository.getReferenceById(itemId);
    }

    @Named("getWarehouseReference")
    protected Warehouse getWarehouseReference(Long warehouseId) {
        return warehouseRepository.getReferenceById(warehouseId);
    }
}

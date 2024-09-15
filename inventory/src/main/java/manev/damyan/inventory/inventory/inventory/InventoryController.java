package manev.damyan.inventory.inventory.inventory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manev.damyan.inventory.inventory.exception.ErrorResponse;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping({ "/warehouses/{warehouse_id}/items/{item_id}", "/items/{item_id}/warehouses/{warehouse_id}" })
    public ResponseEntity<InventoryDTO> addInventory(@PathVariable("warehouse_id") Long warehouseId,
            @PathVariable("item_id") Long itemId, @Valid @RequestBody UpdateInventoryDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.addInventory(warehouseId, itemId, dto));
    }

    @GetMapping({ "/warehouses/{warehouse_id}/items/{item_id}", "/items/{item_id}/warehouses/{warehouse_id}" })
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable(name = "warehouse_id") Long warehouseId,
            @PathVariable(name = "item_id") Long itemId) {
        Optional<InventoryDTO> current = inventoryService.getInventory(warehouseId, itemId);

        if (current.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(current.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getInventories() {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllInventories());
    }

    @GetMapping("/warehouses/{warehouse_id}")
    public ResponseEntity<List<InventoryDTO>> getInventoriesInWarehouse(
            @PathVariable(name = "warehouse_id") Long warehouseId) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllInventoriesForWarehouse(warehouseId));
    }

    @GetMapping("/items/{item_id}")
    public ResponseEntity<List<InventoryDTO>> getInventoriesForItem(@PathVariable(name = "item_id") Long itemId) {
        return ResponseEntity.ok(inventoryService.getAllInventoriesForItem(itemId));
    }

    @PostMapping("/item/{item_id}/decrease")
    public ResponseEntity<InventoryItemDTO> decreaseItemAmount(@PathVariable(name = "item_id") Long itemId, @RequestBody
    UpdateInventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.removeInventory(itemId, dto));
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLockingException(ObjectOptimisticLockingFailureException e,
            WebRequest request) {

        log.info("Tried to update entity concurrently!", e);
        return new ErrorResponse(UUID.randomUUID().toString(), request.getContextPath(),
                "Concurrent update of entity exists! It is being tried to be updated from multiple users!", null);
    }

    @GetMapping("/api/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok("1.0.1-SNAPSHOT");
    }


    private static List<String> buffer = Collections.synchronizedList(new ArrayList<>());
    private static final List<String> INFO = IntStream.range(1, 100001).mapToObj(x -> "SomerandomValue " + x).collect(Collectors.toList());
    @GetMapping("/scaleTest")
    public ResponseEntity<String> scaleTest() {
        buffer.addAll(INFO);
        return ResponseEntity.ok("The size of the list is: " + buffer.size());
    }

    @GetMapping("/scaleTestDown")
    public ResponseEntity<String> scaleTestDown() {
        buffer.clear();
        return ResponseEntity.ok("The size of the list is: " + buffer.size());
    }

}

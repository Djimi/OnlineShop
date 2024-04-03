package manev.damyan.inventory.inventory.warehouse;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import manev.damyan.inventory.inventory.inventory.InventoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService, WarehouseMapper mapper) {
        this.warehouseService = warehouseService;
    }


    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@Valid @RequestBody WarehouseDTO warehouse) {
        WarehouseDTO newWarehouse = warehouseService.createWarehouse(warehouse);
        return new ResponseEntity<>(newWarehouse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseService.getAllWarehouses());

    }


    @GetMapping("{id}")
    public ResponseEntity<WarehouseDTO> getWarehouse(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseService.getWarehouse(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Warehouse> deleteWarehouse(@PathVariable("id") long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(@PathVariable("id") long id, @Valid @RequestBody WarehouseDTO warehouse) {

        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.updateWarehouse(warehouse));
    }
}

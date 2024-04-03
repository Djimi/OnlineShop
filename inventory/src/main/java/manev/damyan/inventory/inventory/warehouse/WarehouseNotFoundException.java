package manev.damyan.inventory.inventory.warehouse;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(long id) {
        super(String.format("Warehouse with id %s not found", id));
    }
}

package manev.damyan.inventory.inventory.items;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemsService itemsService;

    public ItemController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody @Valid ItemDTO dto) {
        if(dto.getId() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(itemsService.create(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable(name = "id") long id) {

        Optional<ItemDTO> dto = itemsService.getItem(id);

        if (dto.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(dto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemsService.getAllItems());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(name = "id") long id) {

        if (itemsService.deleteItem(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable(name = "id") long id, @RequestBody @Valid ItemDTO dto) {

        boolean created = itemsService.update(dto);

        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }
}

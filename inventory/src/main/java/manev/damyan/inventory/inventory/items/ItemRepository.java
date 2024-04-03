package manev.damyan.inventory.inventory.items;

import manev.damyan.inventory.inventory.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

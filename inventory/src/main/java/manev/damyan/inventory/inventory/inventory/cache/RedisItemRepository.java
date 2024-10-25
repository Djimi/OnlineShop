package manev.damyan.inventory.inventory.inventory.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RedisItemRepository extends CrudRepository<RedisInventoryItem, Long> {
    List<RedisInventoryItem> findByName(String name);
}

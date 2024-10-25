package manev.damyan.inventory.inventory.items;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manev.damyan.inventory.inventory.inventory.cache.RedisInventoryItem;
import manev.damyan.inventory.inventory.inventory.cache.RedisItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemsService {

    public static final String ITEMS_CACHE = "items-cache";

    public static final String ALL = "'all'";

    private final ItemRepository itemRepository;

    private final RedisItemRepository redisItemRepository;

    private final ItemMapper mapper;

    @Transactional //this is needed, because of the pessimistic locking
    @CacheEvict(cacheNames = ITEMS_CACHE, key = ALL)
    public boolean update(ItemDTO dto) {
        boolean existing = itemRepository.findByIdWithPessimisticLock(dto.getId()).isPresent();
        itemRepository.save(mapper.convertToEntity(dto));
        return existing;
    }

    @Transactional //this is needed, because of the pessimistic locking
    @CacheEvict(cacheNames = ITEMS_CACHE, key = ALL)
    public ItemDTO create(ItemDTO dto) {

        Item savedItem = itemRepository.save(mapper.convertToEntity(dto));
        redisItemRepository.save(
                new RedisInventoryItem(savedItem.getId(), savedItem.getName(), savedItem.getDetailedDescription()));
        return mapper.convertToDTO(savedItem);
    }

    @CacheEvict(cacheNames = ITEMS_CACHE, key = ALL)
    @Transactional
    public boolean deleteItem(long id) {
        if (!itemRepository.findByIdWithPessimisticLock(id).isPresent()) {
            return false;
        } else {
            itemRepository.deleteById(id);
            return true;
        }
    }

    @Loggable
    @Cacheable(cacheNames = ITEMS_CACHE, key = ALL)
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    public Page<ItemDTO> getPaginated(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)
                .and(Sort.by(Sort.Direction.ASC, "detailedDescription")));
        return itemRepository.findAll(pageRequest).map(mapper::convertToDTO);
    }

    @Transactional //this is needed, because of the pessimistic locking
    public Optional<ItemDTO> getItem(long id) {
        log.debug("Inside getItemId");

        Optional<RedisInventoryItem> redisItem = redisItemRepository.findById(id);

        if (redisItem.isPresent()) {
            RedisInventoryItem redisInventoryItem = redisItem.get();
            return Optional.of(new ItemDTO(redisInventoryItem.getId(), redisInventoryItem.getName(),
                    redisInventoryItem.getDetailedDescription()));
        } else {
            Optional<Item> entity = itemRepository.findById(id);
            if (!entity.isPresent()) {
                return Optional.empty();
            } else {
                ItemDTO value = mapper.convertToDTO(entity.get());
                value.getName();
                return Optional.of(value);

            }

        }

    }

    public List<ItemDTO> getAllByName(String name) {
        List<RedisInventoryItem> item = redisItemRepository.findByName(name);
        if (!item.isEmpty()) {
            return item.stream().map(ri -> new ItemDTO(ri.getId(), ri.getName(), ri.getDetailedDescription())).toList();
        } else {
            return itemRepository.findByName(name).stream().map(mapper::convertToDTO).toList();
        }
    }

    public Optional<List<ItemDTO>> getAllByNameInsensitive(String nameInsensitive) {
        return itemRepository.findByNameIgnoreCase(nameInsensitive)
                .map(entities -> entities.stream().map(mapper::convertToDTO).toList());
    }

    public List<ItemDTO> deleteByName(String name) {
        return itemRepository.deleteByName(name).stream().map(mapper::convertToDTO).toList();
    }

    public List<ItemDTO> getAllItemsByNameWithShortRepresentation(String name, Long id) {
        return itemRepository.findByNameAndId(name, id).stream().map(projection -> {
            ItemDTO responseDTO = new ItemDTO();

            responseDTO.setId(projection.getId());
            responseDTO.setName(projection.getName());
            return responseDTO;
        }).toList();
    }

    public Optional<ItemDTO> fake() {
        if (1 == 1) {
            throw new IllegalArgumentException("Exception thrown in FAKE method (that's expected)");
        }

        return Optional.empty();
    }
}

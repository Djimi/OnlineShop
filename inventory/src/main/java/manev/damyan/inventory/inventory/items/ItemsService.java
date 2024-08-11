package manev.damyan.inventory.inventory.items;

import lombok.RequiredArgsConstructor;
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
public class ItemsService {

    private final ItemRepository itemRepository;

    private final ItemMapper mapper;

    @Transactional //this is needed, because of the pessimistic locking
    public boolean update(ItemDTO dto) {
        boolean existing = itemRepository.findByIdWithPessimisticLock(dto.getId()).isPresent();
        itemRepository.save(mapper.convertToEntity(dto));
        return existing;
    }

    @Transactional //this is needed, because of the pessimistic locking
    public ItemDTO create(ItemDTO dto) {
        return mapper.convertToDTO(itemRepository.save(mapper.convertToEntity(dto)));
    }

    public boolean deleteItem(long id) {
        if (!itemRepository.findByIdWithPessimisticLock(id).isPresent()) {
            return false;
        } else {
            itemRepository.deleteById(id);
            return true;
        }
    }

    @Loggable
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
        System.out.println("Inside getItemId");
        Optional<Item> entity = itemRepository.findById(id);

        if (!entity.isPresent()) {
            return Optional.empty();
        } else {
            ItemDTO value = mapper.convertToDTO(entity.get());
            value.getName();
            return Optional.of(value);

        }

    }

    public Optional<List<Item>> getAllByName(String name) {
        return itemRepository.findByName(name);
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

package manev.damyan.inventory.inventory.items;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemRepository itemRepository;

    private final ItemMapper mapper;

    public boolean update(ItemDTO dto) {
        boolean existing = itemRepository.findById(dto.getId()).isPresent();
        itemRepository.save(mapper.convertToEntity(dto));
        return existing;
    }


    public ItemDTO create(ItemDTO dto){
        return mapper.convertToDTO(itemRepository.save(mapper.convertToEntity(dto)));
    }
    public boolean deleteItem(long id) {
        if (!itemRepository.findById(id).isPresent()) {
            return false;
        } else {
            itemRepository.deleteById(id);
            return true;
        }
    }

    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    public Optional<ItemDTO> getItem(long id) {
        Optional<Item> entity = itemRepository.findById(id);

        if (!entity.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(mapper.convertToDTO(entity.get()));

        }

    }
}

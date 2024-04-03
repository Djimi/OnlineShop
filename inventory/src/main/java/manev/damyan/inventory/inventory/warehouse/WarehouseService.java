package manev.damyan.inventory.inventory.warehouse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    private WarehouseRepository warehouseRepository;

    private WarehouseMapper mapper;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper mapper) {
        this.warehouseRepository = warehouseRepository;
        this.mapper = mapper;
    }

    public WarehouseDTO createWarehouse(WarehouseDTO dto) {
        return mapper.convertToDTO(warehouseRepository.save(mapper.convertToEntity(dto)));
    }

    public void deleteWarehouse(long id) {
        warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException(id));
        warehouseRepository.deleteById(id);
    }

    public WarehouseDTO updateWarehouse(WarehouseDTO dto) {
        if (warehouseRepository.findById(dto.getId()).isPresent()) {
            return mapper.convertToDTO(warehouseRepository.save(mapper.convertToEntity(dto)));
        } else {
            throw new WarehouseNotFoundException(dto.getId());
        }
    }

    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll().stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    public WarehouseDTO getWarehouse(Long id) {
        return warehouseRepository.findById(id).map(mapper::convertToDTO)
                .orElseThrow(() -> new WarehouseNotFoundException(id));
    }
}

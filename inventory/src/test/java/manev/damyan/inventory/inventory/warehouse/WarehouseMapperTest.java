package manev.damyan.inventory.inventory.warehouse;

import manev.damyan.inventory.inventory.country.Country;
import manev.damyan.inventory.inventory.country.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { WarehouseMapperImpl.class })
@EnableJpaRepositories(basePackages = "manev.damyan")
@DataJpaTest
@EntityScan(basePackages = "manev.damyan")
public class WarehouseMapperTest {

    @Autowired
    private WarehouseMapper mapper;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void testConvertFromDTOToEntity() {

        Country country = new Country();
        country.setIso("BGR");
        country.setName("Bulgaria");

        Country countryEntity = countryRepository.save(country);

        WarehouseDTO dto = new WarehouseDTO();
        String countryISO = "BGR";
        String cityName = "Sofia";
        String warehouseName = "SofiaCentralWarehoust";
        String LOCAL_ADDRESS = "Totleben 61";
        dto.setCountry_iso(countryISO);
        dto.setCity(cityName);
        dto.setName(warehouseName);
        dto.setLocalAddress(LOCAL_ADDRESS);

        Warehouse result = mapper.convertToEntity(dto);

        Assertions.assertEquals(result.getId(), dto.getId());
        Assertions.assertEquals(countryISO, result.getCountry().getIso());
        Assertions.assertEquals(warehouseName, dto.getName());
        Assertions.assertEquals(result.getAddress(), String.format("%s-%s", cityName, LOCAL_ADDRESS));
    }

    @Test
    public void testConvertFromEntityToDTO() {

        Country country = new Country();
        country.setIso("BGR");
        country.setName("Bulgaria");

        Country countryEntity = countryRepository.save(country);

        Warehouse warehouse = new Warehouse();
        warehouse.setCountry(countryEntity);
        warehouse.setName("Plovdid warehouse");
        warehouse.setAddress("Sofia-Totleben 61");

        Warehouse warehouseEntity = warehouseRepository.save(warehouse);

        WarehouseDTO result = mapper.convertToDTO(warehouseEntity);

        String address = warehouseEntity.getAddress();

        Assertions.assertEquals(result.getId(), warehouse.getId());
        Assertions.assertEquals(result.getCountry_iso(), warehouse.getCountry().getIso());
        Assertions.assertEquals(result.getName(), warehouse.getName());
        Assertions.assertEquals(result.getCity(), address.substring(0, address.indexOf("-")));
        Assertions.assertEquals(result.getLocalAddress(), address.substring(address.indexOf("-")));

    }
}

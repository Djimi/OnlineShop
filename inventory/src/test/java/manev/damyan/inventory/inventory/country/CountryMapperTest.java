package manev.damyan.inventory.inventory.country;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//SpringBootTest(classes = {ConfigurationMapperImpl.class, SubMapper1Impl.class, SubMapper2Impl.class})
@ContextConfiguration(classes = {
            CountryMapperImpl.class,
        })
public class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;

//    @Test
    public void testDTOToEntity() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setIso("BG");
        countryDTO.setName("Bulgaria");

        Country country = countryMapper.convertToEntity(countryDTO);

        Assertions.assertEquals(countryDTO.getIso(), country.getIso());
        Assertions.assertEquals(countryDTO.getName(), country.getName());
    }

//    @Test
    public void testEntityToDTO() {
        Country entity = new Country();
        entity.setIso("BG");
        entity.setName("Bulgaria");

        CountryDTO dto = countryMapper.convertToDTO(entity);

        Assertions.assertEquals(entity.getIso(), dto.getIso());
        Assertions.assertEquals(entity.getName(), dto.getName());
    }
}

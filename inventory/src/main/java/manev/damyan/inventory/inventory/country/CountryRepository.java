package manev.damyan.inventory.inventory.country;

import manev.damyan.inventory.inventory.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}

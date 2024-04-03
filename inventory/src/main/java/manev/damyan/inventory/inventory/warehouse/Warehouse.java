package manev.damyan.inventory.inventory.warehouse;

import jakarta.persistence.*;
import lombok.Data;
import manev.damyan.inventory.inventory.country.Country;
import manev.damyan.inventory.inventory.inventory.Inventory;

import java.util.List;

@Entity
@Table(name = "warehouse")
@Data
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "country_iso")
    @ManyToOne
    private Country country;

    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "warehouse")
    List<Inventory> inventories;
}

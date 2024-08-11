package manev.damyan.inventory.inventory.items;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.Lock;

@Entity
@Table(name = "item")
@NamedQuery(name = "search_by_name_insensitive", query = "SELECT i FROM Item i where UPPER(i.name) = UPPER(:nameCaseInsensitive)")
@Data
public class Item extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String detailedDescription;

//    @OneToMany(mappedBy = "item")
//    private List<Inventory> inventories;
}

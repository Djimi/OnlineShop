package manev.damyan.inventory.inventory.items;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class Item {

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

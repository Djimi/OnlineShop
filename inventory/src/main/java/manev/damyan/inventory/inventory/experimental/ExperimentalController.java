package manev.damyan.inventory.inventory.experimental;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentalController {

    private TestBean testBean10;


    private TestBean testBean11;


    private TestBean testBean20;

    private TestBean testBean21;

    public ExperimentalController(@Qualifier("Bean1") TestBean testBean10,
            @Qualifier("Bean1") TestBean testBean11,
            @Qualifier("Bean2") TestBean testBean20,
            @Qualifier("Bean2") TestBean testBean21) {
        this.testBean10 = testBean10;
        this.testBean11 = testBean11;
        this.testBean20 = testBean20;
        this.testBean21=testBean21;
    }

    @GetMapping("/experimental1")
    public ResponseEntity<?> experimentalOne() {
        return ResponseEntity.ok("Beans names are: " + testBean10.getId()
        + " and " + testBean11.getId());
    }

    @GetMapping("/experimental2")
    public ResponseEntity<?> experimentalTwo() {
        return ResponseEntity.ok("Bean name is: " + testBean20.getId()
        + " and " + testBean21);
    }
}

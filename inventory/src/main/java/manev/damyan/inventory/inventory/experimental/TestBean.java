package manev.damyan.inventory.inventory.experimental;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class TestBean {

    private String id;

    public TestBean(String id) {
        this.id = id;
    }

    public void initMethod() {
        log.debug("Bean created: " + id);
    }

    public void destroyMethod() {
        log.debug("Bean destroyed: " + id);
    }
}

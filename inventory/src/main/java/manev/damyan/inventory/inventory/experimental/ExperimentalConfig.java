package manev.damyan.inventory.inventory.experimental;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.UUID;

@Configuration
public class ExperimentalConfig {

    @Qualifier("Bean1")
    @Scope(scopeName = "singleton")
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public TestBean getBean1() {
        return new TestBean(UUID.randomUUID().toString());
    }

    @Qualifier("Bean2")
    @Scope(scopeName = "prototype")
    @ApplicationScope
    @Bean
    public TestBean getBean2() {
        return new TestBean(UUID.randomUUID().toString());
    }
}

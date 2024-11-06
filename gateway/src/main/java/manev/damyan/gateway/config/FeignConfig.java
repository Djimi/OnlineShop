package manev.damyan.gateway.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    ObjectFactory<HttpMessageConverters> messageConverters = () -> {
        HttpMessageConverters converters = new HttpMessageConverters();
        return converters;
    };

    @Bean
    public Decoder decoder() {

        return new SpringDecoder(messageConverters);
    }

    @Bean
    public Encoder encoder() {
        return new SpringEncoder(messageConverters);
    }
}

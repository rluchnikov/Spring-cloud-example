package java.com.github.example.catalog.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Contract useFeignAnnotations() {
        return new Contract.Default();
    }
}

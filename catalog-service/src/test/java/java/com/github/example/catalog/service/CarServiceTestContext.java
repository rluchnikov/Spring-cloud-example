package java.com.github.example.catalog.service;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import java.com.github.example.catalog.client.AuthServiceClient;
import java.com.github.example.catalog.repository.CarsRepository;
import java.com.github.example.catalog.repository.LocationsRepository;
import java.com.github.example.catalog.repository.StateRepository;

@SpringBootConfiguration
@TestConfiguration
public class CarServiceTestContext {

    @Bean
    public CarsRepository carsRepository() {
        return Mockito.mock(CarsRepository.class);
    }

    @Bean
    public StateRepository stateRepository() {
        return Mockito.mock(StateRepository.class);
    }

    @Bean
    public LocationsRepository locationsRepository() {
        return Mockito.mock(LocationsRepository.class);
    }

    @Bean
    public AuthServiceClient authServiceClient() {
        return Mockito.mock(AuthServiceClient.class);
    }

    @Bean(name="CarServiceTest")
    public CarsService carsService() {
        return new CarsServiceImpl();
    }
}

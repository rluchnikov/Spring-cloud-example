package java.com.github.example.catalog.service;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.com.github.example.catalog.client.AuthServiceClient;
import java.com.github.example.catalog.entity.Locations;
import java.com.github.example.catalog.repository.CarsRepository;
import java.com.github.example.catalog.repository.LocationsRepository;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;


@SpringBootTest
@Import(CarServiceTestContext.class)
@TestPropertySource(locations = "classpath:application-repository-tests.properties")
public class CarsServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("CarServiceTest")
    CarsService carsService;

    @Autowired
    AuthServiceClient authServiceClient;

    @Autowired
    CarsRepository carsRepository;

    @Autowired
    LocationsRepository locationsRepository;
    private Authentication authentication;

    @BeforeClass
    public void init() {
        authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Mockito.when(authServiceClient.getLocation(any())).thenReturn("test");
        Mockito.when(locationsRepository.findByName("test")).thenReturn(Locations.builder().id(1).build());
    }

    @Test
    public void findCarsByRentManager() {
        Mockito.when(authentication.getAuthorities())
                .thenAnswer(invocationOnMock -> Collections.singletonList(new SimpleGrantedAuthority("RENT_MANAGER")));
        carsService.findAll();
        Mockito.verify(carsRepository).findCarsByLocation(1);
    }

    @Test
    public void findCarsByBOSS() {
        Mockito.when(authentication.getAuthorities())
                .thenAnswer(invocationOnMock -> Collections.singletonList(new SimpleGrantedAuthority("BOSS")));
        carsService.findAll();
        Mockito.verify(carsRepository).findAll();
    }

    @Test
    public void findCarsByServiceManager() {
        Mockito.when(authentication.getAuthorities())
                .thenAnswer(invocationOnMock -> Collections.singletonList(new SimpleGrantedAuthority("SERVICE_MANAGER")));
        carsService.findAll();
        Mockito.verify(carsRepository).findCarsByState(anyString());
    }
}
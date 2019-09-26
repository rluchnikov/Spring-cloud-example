package java.com.github.example.catalog.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.com.github.example.catalog.CatalogTest;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.entity.Locations;
import java.com.github.example.catalog.entity.Statuses;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = CatalogTest.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-repository-tests.properties")
public class CarsRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    CarsRepository repository;

    @Autowired
    LocationsRepository locationsRepository;

    @Autowired
    StateRepository stateRepository;

    private Locations locations42;
    private Locations locations6;
    private Statuses goState;

    @BeforeClass
    public void init() {
        locations42 = locationsRepository.saveAndFlush(Locations.builder().name("42").build());
        locations6 = locationsRepository.saveAndFlush(Locations.builder().name("6").build());

        Statuses stopState = stateRepository.saveAndFlush(Statuses.builder().name("stop").build());
        goState = stateRepository.saveAndFlush(Statuses.builder().name("go").build());


        Car car1 = Car.builder().id(1)
                .location(Locations.builder().id(locations42.getId()).build())
                .planned_next_state(LocalDate.of(2016, 1, 1))
                .state(stopState)
                .next_state(goState)
                .build();
        repository.saveAndFlush(car1);
        Car car2 = Car.builder().id(2)
                .location(Locations.builder().id(locations6.getId()).build())
                .planned_next_state(LocalDate.of(2017, 2, 2))
                .state(stopState)
                .next_state(stopState)
                .build();
        repository.saveAndFlush(car2);
        Car car3 = Car.builder().id(3)
                .location(Locations.builder().id(locations42.getId()).build())
                .planned_next_state(LocalDate.of(2018, 3, 3))
                .state(goState)
                .next_state(stopState)
                .build();
        repository.saveAndFlush(car3);
    }

    @Test
    public void getCarSuccess() {
        List<Car> carsByLocation = repository.findCarsByLocation(locations42.getId());
        Assert.assertEquals(2, carsByLocation.size());
    }

    @Test
    public void findCarsByLocationWithDate() {
        List<Car> carsByLocationWithDate = repository.findCarsByLocationWithDate(
                locations42.getId(),
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1)
        );

        Assert.assertEquals(0, carsByLocationWithDate.size());
    }

    @Test
    public void findAllWithDate() {
        List<Car> carsByLocationWithDate = repository.findAllWithDate(
                LocalDate.of(2017, 1, 1),
                LocalDate.of(2100, 1, 1)
        );

        Assert.assertEquals(2, carsByLocationWithDate.size());
    }

    @Test
    public void findCarsByStateWithDate() {
        List<Car> carsByLocationWithDate = repository.findCarsByStateWithDate(goState.getName(), LocalDate.MIN, LocalDate.MAX);

        Assert.assertEquals(2, carsByLocationWithDate.size());
    }
}
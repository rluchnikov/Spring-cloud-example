package java.com.github.example.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.com.github.example.catalog.CatalogTest;
import java.com.github.example.catalog.dto.CarDtoPost;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.repository.CarsRepository;
import java.com.github.example.catalog.repository.LocationsRepository;
import java.com.github.example.catalog.repository.StateRepository;
import java.com.github.example.catalog.service.CarsService;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CatalogTest.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-repository-tests.properties")
public class CarsControllerTest extends AbstractTestNGSpringContextTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private CarsController carsController;

    @Mock
    private CarsService carService;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private CarsRepository carsRepository;

    @Mock
    private LocationsRepository locationRepository;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(carsController).build();
    }

    @Test
    public void testCreateSuccess() throws Exception {
        CarDtoPost carDtoPost = new CarDtoPost();
        carDtoPost.setLocation(42);
        carDtoPost.setBrand("Jet");

        String json = mapper.writeValueAsString(carDtoPost);

        mockMvc.perform(post("/car").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Mockito.verify(carsRepository, times(1)).save(Mockito.any(Car.class));
    }

    @Test
    public void updateCarFail() throws Exception {
        Mockito.when(carsRepository.findById(42)).thenReturn(Optional.empty());

        String json = mapper.writeValueAsString(new Car());

        mockMvc.perform(put("/car/42").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());

        Mockito.verify(carsRepository, times(0)).save(Mockito.any(Car.class));
    }

}
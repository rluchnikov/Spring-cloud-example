package java.com.github.example.state.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.com.github.example.state.StateTest;
import java.com.github.example.state.dto.StateDto;
import java.com.github.example.state.repository.OrderStatesRepository;
import java.com.github.example.state.service.StateService;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StateTest.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class StateControllerTest extends AbstractTestNGSpringContextTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateService;

    @Mock
    private OrderStatesRepository orderStatesRepository;

    private MockMvc mockMvc;


    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stateController).build();
    }

    @Test
    public void receiveEvent() throws Exception {
        String json = mapper.writeValueAsString(StateDto.builder().id(1).state("Исполнение").build());
        mockMvc.perform(post("/orders/receive/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Mockito.verify(stateService, Mockito.times(1)).changeState(1, "Исполнение");
    }

    @Test
    public void getEvent() throws Exception {
        StateDto stateDto = StateDto.builder().id(1).state("stop").build();

        String json = mapper.writeValueAsString(stateDto);

        mockMvc.perform(post("/orders/receive").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Mockito.verify(stateService, Mockito.times(1)).changeState(1, "stop");
    }
}
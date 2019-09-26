package java.com.github.example.authservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.com.github.example.authservice.controller.UserController;
import java.com.github.example.authservice.dto.UserDto;
import java.com.github.example.authservice.entity.Role;
import java.com.github.example.authservice.repository.RoleRepository;
import java.com.github.example.authservice.service.UserService;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private UserController accountController;

    @Mock
    RoleRepository roleRepository;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }


    @Test
    public void shouldCreateNewUser() throws Exception {
        roleRepository.saveAndFlush(Role.builder().id(1).roleName("ADMIN").build());
        String json = mapper.writeValueAsString(UserDto.builder().id(1).firstname("test").lastname("test").middlename("test").password("password").username("test")
                .role(1).location("Пункт проката №1").birthday("12-02-1981").appointment_date("01-01-2018").mobile("89268788002").build());

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        mockMvc.perform(get("/user").principal(new UserPrincipal("test")))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(status().isOk());
    }
}

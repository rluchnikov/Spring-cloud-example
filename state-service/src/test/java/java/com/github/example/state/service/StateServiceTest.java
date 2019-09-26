package java.com.github.example.state.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Import;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.com.github.example.state.client.CatalogClient;
import java.com.github.example.state.entity.ContextEntity;
import java.com.github.example.state.entity.OrderStates;
import java.com.github.example.state.repository.OrderStatesRepository;
import java.com.github.example.state.statemachine.DefaultStateMachineAdapter;

import java.io.Serializable;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.reset;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(StateServiceTestContext.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class StateServiceTest {

    @Autowired
    StateService stateService;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private OrderStatesRepository orderRepository;

    @Autowired
    private StateMachineFactory<String, String> stateMachineFactory;

    @Autowired
    Source mysource;

    @Autowired
    private DefaultStateMachineAdapter<String, String, ContextEntity<String, String, ? extends Serializable>> orderStateMachineAdapter;

    @Before
    public void init() {
        reset(orderStateMachineAdapter, catalogClient, orderRepository);
    }

    @Test
    public void persistStateNoExists() {
        StateMachine<String, String> stateMachine = Mockito.mock(StateMachine.class);
        Mockito.when(orderStateMachineAdapter.restore(any())).thenReturn(stateMachine);
        Mockito.when(orderRepository.findByOrderId(0)).thenReturn(Optional.of(new OrderStates()));

        stateService.persistState(0, "foobar", 3, null,null);


        Mockito.verify(orderStateMachineAdapter).persist(anyObject(), any(OrderStates.class));
        Mockito.verify(orderRepository).save(any(OrderStates.class));
    }

    @Test
    public void persistStateExists() {
        StateMachine<String, String> stateMachine = Mockito.mock(StateMachine.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(stateMachineFactory.getStateMachine()).thenReturn(stateMachine);
        Mockito.when(orderRepository.findByOrderId(0)).thenReturn(Optional.empty());

        stateService.persistState(0, "foobar", 3, null,null);

        Mockito.verify(stateMachine).sendEvent("foobar");
        Mockito.verify(orderStateMachineAdapter).persist(anyObject(), any(OrderStates.class));
        Mockito.verify(orderRepository).save(any(OrderStates.class));
    }

}
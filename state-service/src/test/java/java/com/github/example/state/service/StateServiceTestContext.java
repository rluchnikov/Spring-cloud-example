package java.com.github.example.state.service;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.StateMachineFactory;
import java.com.github.example.state.client.CatalogClient;
import java.com.github.example.state.entity.ContextEntity;
import java.com.github.example.state.entity.OrderStates;
import java.com.github.example.state.repository.OrderStatesRepository;
import java.com.github.example.state.statemachine.DefaultStateMachineAdapter;

import java.io.Serializable;

@SpringBootConfiguration
@TestConfiguration
public class StateServiceTestContext {

    @Bean
    OrderStates orderStates() {
        return Mockito.mock(OrderStates.class);
    }

    @Bean
    CatalogClient catalogClient() {
        return Mockito.mock(CatalogClient.class);
    }

    @Bean
    Source mySource() {
        return Mockito.mock(Source.class);
    }

    @Bean
    OrderStatesRepository orderStatesRepository() {
        return Mockito.mock(OrderStatesRepository.class);
    }

    @Bean
    StateMachineFactory<String, String> stateMachineFactory() {
        return Mockito.mock(StateMachineFactory.class);
    }

    @Bean
    DefaultStateMachineAdapter<String, String, ContextEntity<String, String, ? extends Serializable>> orderStateMachineAdapter() {
        return Mockito.mock(DefaultStateMachineAdapter.class);
    }

    @Bean
    StateService stateService(DefaultStateMachineAdapter orderStateMachineAdapter) {
        return new StateServiceImpl(orderStateMachineAdapter);
    }

}

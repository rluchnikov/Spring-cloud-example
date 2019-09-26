package java.com.github.example.state.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import java.com.github.example.state.entity.ContextEntity;

import java.io.Serializable;

@Configuration
public class DefaultStateMachineAdapterConfig {

    @Bean
    public DefaultStateMachineAdapter<String, String, ContextEntity<String, String, Serializable>> orderStateMachineAdapter(
            StateMachineFactory<String, String> stateMachineFactory,
            StateMachinePersister<String, String, ContextEntity<String, String, Serializable>> persister) {
        return new DefaultStateMachineAdapter<>(stateMachineFactory, persister);
    }

    @Bean
    public StateMachinePersister<String, String, ContextEntity<String, String, Serializable>> persister(
            StateMachinePersist<String, String, ContextEntity<String, String, Serializable>> persist) {
        return new DefaultStateMachinePersister<>(persist);
    }

    @Bean
    public StateMachinePersist<String, String, ContextEntity<String, String, Serializable>> persist() {
        return new StateMachinePersist<String, String, ContextEntity<String, String, Serializable>>() {

            @Override
            public StateMachineContext<String, String> read(
                    ContextEntity<String, String, Serializable> order) throws Exception {
                return order.getStateMachineContext();
            }

            @Override
            public void write(StateMachineContext<String, String> context,
                              ContextEntity<String, String, Serializable> order) throws Exception {
                order.setStateMachineContext(context);
            }
        };
    }

}

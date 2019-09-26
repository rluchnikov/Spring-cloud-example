package java.com.github.example.state.statemachine;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.UUID;


@RequiredArgsConstructor
public class DefaultStateMachineAdapter<S, E, T> {

    final StateMachineFactory<String, String> stateMachineFactory;

    final StateMachinePersister<String, String, T> persister;

    @SneakyThrows
    public StateMachine<String, String> restore(T contextObject) {
        StateMachine<String, String> stateMachine = stateMachineFactory.getStateMachine();
        return persister.restore(stateMachine, contextObject);
    }

    @SneakyThrows
    public void persist(StateMachine<String, String> stateMachine, T order) {
        persister.persist(stateMachine, order);
    }

    public StateMachine<String, String> create() {
        StateMachine<String, String> stateMachine = stateMachineFactory.getStateMachine(UUID.randomUUID().toString());
        stateMachine.start();
        return stateMachine;
    }
}

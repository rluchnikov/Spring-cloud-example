package java.com.github.example.state.entity;

import org.springframework.statemachine.StateMachineContext;

import java.io.Serializable;

public interface ContextEntity<S, E, ID>  extends Serializable {

    StateMachineContext<S, E> getStateMachineContext();

    void setStateMachineContext(StateMachineContext<S, E> context);

}
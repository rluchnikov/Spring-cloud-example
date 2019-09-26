package java.com.github.example.state.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.statemachine.StateMachineContext;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Access(AccessType.FIELD)
@Table(name="order_states")
public class OrderStates extends AbstractPersistable<Long> implements ContextEntity<String, String, Long> {

    private static final long serialVersionUID = 8848887579564649636L;

    @Getter
    StateMachineContext<String, String> stateMachineContext;

    @Getter
    @Setter
    private Integer orderId;

    @Getter
    @Setter
    private Integer carId;

    @Getter
    @Setter
    String currentState;

    @Getter
    @Setter
    private LocalDate createdAt;

    @Getter
    @Setter
    private LocalDate lastModifiedAt;


    @Override
    public void setStateMachineContext(@NonNull StateMachineContext<String, String> stateMachineContext) {
        this.currentState = stateMachineContext.getState();
        this.stateMachineContext = stateMachineContext;
    }


}

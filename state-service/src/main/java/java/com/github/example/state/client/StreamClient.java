package java.com.github.example.state.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.com.github.example.state.entity.ContextEntity;
import java.com.github.example.state.messages.StatusEvent;
import java.com.github.example.state.service.StateService;
import java.com.github.example.state.statemachine.DefaultStateMachineAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@EnableBinding(Sink.class)
@Component
public class StreamClient {

    @Autowired
    public StateService stateService;

    @Autowired
    DefaultStateMachineAdapter<String, String, ContextEntity<String, String, ? extends Serializable>> orderStateMachineAdapter;

    @StreamListener(Sink.INPUT)
    public void streamListener(@Payload StatusEvent event) {
        if (event != null) {
            log.info("get message " + event.getOrderId());
            stateService.persistState(event.getOrderId(), "Создан", event.getCarId(), LocalDate.parse(event.getStartRentdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    LocalDate.parse(event.getEndRentdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
}

package java.com.github.example.state.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import java.com.github.example.state.entity.ContextEntity;
import java.com.github.example.state.entity.OrderStates;
import java.com.github.example.state.messages.CatalogEvent;
import java.com.github.example.state.repository.OrderStatesRepository;
import java.com.github.example.state.statemachine.DefaultStateMachineAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    public OrderStates orderStates;

    @Autowired
    Source mysource;

    @Autowired
    private OrderStatesRepository orderRepository;

    @Autowired
    private StateMachineFactory<String, String> stateMachineFactory;

    final DefaultStateMachineAdapter<String, String, ContextEntity<String, String, ? extends Serializable>> orderStateMachineAdapter;

    @Override
    public  OrderStates persistState(Integer id, String event, Integer carid,LocalDate rentDate,LocalDate endDate){
        Optional<OrderStates> exist = orderRepository.findByOrderId(id);
        orderStates = new OrderStates();
        if (!exist.isPresent()) {
            StateMachine<String, String> stateMachine = stateMachineFactory.getStateMachine();
            stateMachine.start();
            orderStates.setOrderId(id);
            orderStates.setCarId(carid);
            orderStates.setCreatedAt(LocalDate.now());
            stateMachine.getExtendedState().getVariables().put("orderID",id);
            if (carid != null) {
                stateMachine.getExtendedState().getVariables().put("carID", carid);
            }
            if  (rentDate != null) {
                stateMachine.getExtendedState().getVariables().put("rentDate", rentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
            if  (endDate != null) {
                stateMachine.getExtendedState().getVariables().put("endDate", endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
            stateMachine.sendEvent(event);
            orderStateMachineAdapter.persist(stateMachine, orderStates);
            log.info("Зафиксирован заказ "+ id +" для машины " + carid +" дата проката " +rentDate);
        }else {
            StateMachine<String,String> stateMachine = orderStateMachineAdapter.restore(exist.get());
            stateMachine.sendEvent(event);
            orderStates.setLastModifiedAt(LocalDate.now());
            orderStateMachineAdapter.persist(stateMachine, orderStates);
        }
        if (id !=0)
        mysource.output().send(MessageBuilder.withPayload(CatalogEvent.builder().carId(carid)
                .status("start").orderId(id)
                .startRentdate(rentDate !=null ? rentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")):null)
                .endRentdate(endDate !=null ? endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")):null).build()).build());
           return  orderRepository.save(orderStates);
    }

    @Override
    public List<String> changeState(Integer id, String event){
        orderStates = orderRepository.findByOrderId(id).orElseThrow
                (() -> new RuntimeException("No value"));
        StateMachine<String,String> stateMachine = orderStateMachineAdapter.restore(orderStates);
        log.info("Принято событие" + event);
        stateMachine.sendEvent(event);
        orderStates.setLastModifiedAt(LocalDate.now());
        orderStates.setCurrentState(event);
        orderStateMachineAdapter.persist(stateMachine, orderStates);
        orderRepository.save(orderStates);
        return stateMachine.getTransitions().stream().filter(x -> x.getSource().getId().equals(stateMachine.getState().getId())).map(p->p.getTarget().getId())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

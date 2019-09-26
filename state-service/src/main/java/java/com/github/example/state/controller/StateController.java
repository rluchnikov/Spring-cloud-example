package java.com.github.example.state.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;
import java.com.github.example.state.dto.StateDto;
import java.com.github.example.state.entity.ContextEntity;
import java.com.github.example.state.repository.OrderStatesRepository;
import java.com.github.example.state.service.StateService;
import java.com.github.example.state.statemachine.DefaultStateMachineAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StateController {

    @Autowired
    public StateService stateService;
    @Autowired
    OrderStatesRepository orderRepository;

    @Autowired
    DefaultStateMachineAdapter<String, String, ContextEntity<String, String, ? extends Serializable>> orderStateMachineAdapter;

    @GetMapping(path = "/orders/{id}/states")
     public List<String> receiveEvent(@PathVariable("id") Integer id) {
         StateMachine<String, String> stateMachine = orderStateMachineAdapter.restore(orderRepository.findByOrderId(id).orElseThrow
                 (() -> new RuntimeException("No value")));
          return stateMachine.getTransitions().stream().filter(x -> x.getSource().getId().equals(stateMachine.getState().getId())).map(p->p.getTarget().getId())
                 .collect(Collectors.toCollection(ArrayList::new));


     }

    @PostMapping(path = "/orders/receive")
    public List<String>  getEvent(@RequestBody StateDto state) {
        return stateService.changeState(state.getId(),state.getState());

    }
}

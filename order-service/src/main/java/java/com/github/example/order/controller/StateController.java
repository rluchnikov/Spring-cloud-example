package java.com.github.example.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.com.github.example.order.entity.Statuses;
import java.com.github.example.order.repository.StateRepository;

import java.util.List;

@RestController
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping(value="/statuses")
    public List<Statuses> listStatuses() {
        return stateRepository.findAll();
    }
}

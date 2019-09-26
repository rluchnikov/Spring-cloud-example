package java.com.github.example.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.com.github.example.catalog.entity.Statuses;
import java.com.github.example.catalog.repository.StateRepository;

import java.util.List;

@RestController
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping(value="/cars/statuses")
    public List<Statuses> listStatuses() {
        return stateRepository.findAll();
    }
}

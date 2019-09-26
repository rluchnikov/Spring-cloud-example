package java.com.github.example.state.service;

import java.com.github.example.state.entity.OrderStates;

import java.time.LocalDate;
import java.util.List;

public interface StateService {

    OrderStates persistState(Integer id, String event, Integer carid, LocalDate rentDate, LocalDate endDate);
    List<String> changeState(Integer id, String event);
}

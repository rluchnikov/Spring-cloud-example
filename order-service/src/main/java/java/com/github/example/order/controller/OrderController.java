package java.com.github.example.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.com.github.example.order.entity.Order;
import java.com.github.example.order.exception.OrderDateException;
import java.com.github.example.order.repository.StateRepository;
import java.com.github.example.order.service.OrderService;

import java.util.List;

@Slf4j
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/order")
    public Order create(@RequestBody Order order) {
      if  (orderService.checkDate(order)){
          throw new OrderDateException();
        }
     return orderService.create(order);
    }

    @PutMapping(value = "/order/{id}")
    public Order update(@PathVariable("id") Integer id,
                        @RequestBody Order order) {
        return orderService.update(id, order);
    }

    @DeleteMapping(value = "/order/{id}")
    public void delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
    }

    @GetMapping(value = "/order/{id}")
    public Order get(@PathVariable("id") Integer id) {
        return orderService.get(id);
    }

    @GetMapping(value = "/orders")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PutMapping(value = "/order/{id}/{status}")
    public Order changeStatus(@PathVariable("id") Integer id,
                         @PathVariable("status") String status) {
     Order order  = orderService.get(id);
     order.setStatus(stateRepository.findByName(status));
     log.info("Статус изменился на " +status);
     return orderService.update(id, order);
    }

}

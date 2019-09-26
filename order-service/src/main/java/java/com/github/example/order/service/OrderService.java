package java.com.github.example.order.service;

import java.com.github.example.order.entity.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order update(Integer id, Order order);

    void delete(Integer id);

    Order get(Integer id);

    List<Order> getAll();

    boolean checkDate(Order order);

}

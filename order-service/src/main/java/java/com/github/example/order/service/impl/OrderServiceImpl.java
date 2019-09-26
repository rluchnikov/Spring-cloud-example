package java.com.github.example.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import java.com.github.example.order.entity.Order;
import java.com.github.example.order.exception.OrderNotFoundException;
import java.com.github.example.order.messages.StatusEvent;
import java.com.github.example.order.repository.OrderRepository;
import java.com.github.example.order.repository.StateRepository;
import java.com.github.example.order.service.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    Source mysource;

    @Override
    public Order create(Order order) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            order.setManger(authentication.getName());
        }
        order.setStatus(stateRepository.findOne(1));
        order.setCreateDate(LocalDateTime.now());
        Order newOrder = orderRepository.saveAndFlush(order);
        //send message
        if (newOrder != null) {
          final StatusEvent statusEvent = StatusEvent.builder().orderId(newOrder.getId()).carId(newOrder.getCarId()).
                    startRentdate(newOrder.getStartRentDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                  endRentdate(newOrder.getEndRantDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).build();
            mysource.output().send(MessageBuilder.withPayload(statusEvent)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
            log.info("send message " + statusEvent);
        }
       return newOrder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order update(Integer id, Order order) {
        checkOnAvailability(orderRepository.findOne(id), id);
        return orderRepository.save(order);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Order order = orderRepository.findOne(id);
        checkOnAvailability(order, id);
        orderRepository.delete(id);
    }

    @Override
    public Order get(Integer id) {
        Order order = orderRepository.findOne(id);
        checkOnAvailability(order, id);
        return order;
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orderRepository.findAll());
    }

    private void checkOnAvailability(Order order, Integer id) {
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
    }

    public boolean checkDate(Order order) {
      return  orderRepository.checkDate(order.getCarId(),order.getStartRentDate(),order.getEndRantDate());

    }

}

package java.com.github.example.order.service;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.com.github.example.order.entity.Order;
import java.com.github.example.order.entity.Statuses;
import java.com.github.example.order.repository.OrderRepository;
import java.com.github.example.order.repository.StateRepository;
import java.com.github.example.order.service.impl.OrderServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest extends AbstractTestNGSpringContextTests {

    private Order order;
    private Statuses state;
    private Authentication authentication;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private OrderService orderService;

   @TestConfiguration
    static  class EmployeeServiceImplTestContextConfiguration {

       @Bean
       public OrderRepository orderRepository() {
           return Mockito.mock(OrderRepository.class);
       }

       @Bean
       public StateRepository stateRepository() {
           return Mockito.mock(StateRepository.class);
       }

        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }

        @Bean
        Source source() {
            return Mockito.mock(Source.class);
        }
    }


    @BeforeClass
    public void setUp() {
        authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        state = Statuses.builder().id(1).name("Создан").build();
        order = Order.builder()
                .carId(1)
                .lastName("LastName")
                .firstName("FirstName")
                .middleName("middleName")
                .clientPhoneNumber("ClientPhone")
                .clientEmail("Mail")
                .startRentDate(LocalDate.now())
                .endRantDate(LocalDate.now().plusDays(1))
                .locationStart("Пункт проката №1")
                .locationEnd("Пункт проката №2")
                .status(state)
                .manger("admin")
                .CreateDate(LocalDateTime.now())
                .comment("Comment")
                .build();
    }

    @Test
    public void getAllOrdersTest() {
        Order order2 = Order.builder()
                .carId(2)
                .lastName("LastName2")
                .firstName("FirstName2")
                .middleName("middleName2")
                .clientPhoneNumber("ClientPhone2")
                .clientEmail("Mail2")
                .startRentDate(LocalDate.now())
                .endRantDate(LocalDate.now().plusDays(1))
                .locationStart("Пункт проката №1")
                .locationEnd("Пункт проката №2")
                .status(state)
                .manger("admin")
                .CreateDate(LocalDateTime.now())
                .comment("Comment")
                .build();

        when(orderService.getAll()).thenReturn(new ArrayList<Order>() {{
            add(order);
            add(order2);
        }});

        List<Order> orders = orderService.getAll();

        Assert.assertEquals(orders.size(), 2);
    }

    @Test
    public void deleteOrderTest() {
        order.setId(1);
        when(orderRepository.findOne(order.getId())).thenReturn(order);
        orderService.delete(order.getId());
    }

    @Test
    public void updateOrderTest() {
        order.setId(1);
        when(orderRepository.findOne(order.getId())).thenReturn(order);
        order.setCarId(2);
        when(orderRepository.save(order)).thenReturn(order);
        Order dbOrder = orderService.update(1, order);
        Assert.assertEquals(order, dbOrder);
    }

}

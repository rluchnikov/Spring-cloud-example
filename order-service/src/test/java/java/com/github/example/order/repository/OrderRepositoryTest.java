package java.com.github.example.order.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.com.github.example.order.OrderTest;
import java.com.github.example.order.entity.Order;
import java.com.github.example.order.entity.Statuses;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest(classes = OrderTest.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StateRepository stateRepository;

    private Statuses state;
    private Order order;

    @BeforeClass
    public void setUp() {
        state = Statuses.builder().id(1).name("Создан").build();
        Statuses newState = stateRepository.save(state);
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
    public void createOrderTest() {
        Statuses newState = stateRepository.save(state);
        Order dbOrder =  orderRepository.save(order);
        order.setId(dbOrder.getId());
        Assert.assertEquals(dbOrder, order);
    }

    @Test
    public void getOrderTest() {
        Order dbOrder = orderRepository.save(order);
        Order newOrder = orderRepository.findOne(dbOrder.getId());
        Assert.assertEquals(dbOrder, newOrder);
    }

    @Test
    public void getAllOrdersTest() {
        Order dbOrder1 = orderRepository.save(order);

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
                .comment("Comment2")
                .build();

        Order dbOrder2 = orderRepository.save(order2);
        Assert.assertEquals(dbOrder1, orderRepository.findOne(dbOrder1.getId()));
        Assert.assertEquals(dbOrder2, orderRepository.findOne(dbOrder2.getId()));
    }

    @Test
    public void deleteOrderTest() {
        Order dbOrder = orderRepository.save(order);
        orderRepository.delete(dbOrder.getId());
        try {
            orderRepository.getOne(dbOrder.getId());
        } catch (JpaObjectRetrievalFailureException e) {
            // Сущность не найдена
        }
    }

    @Test
    public void updateOrderTest() {
        Order dbOrder = orderRepository.save(order);
        dbOrder.setCarId(2);
        Order updatedDBOrder = orderRepository.save(dbOrder);
        Assert.assertEquals(dbOrder, updatedDBOrder);
    }

}

package java.com.github.example.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.com.github.example.order.entity.Order;

import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Order u WHERE u.carId = :id " +
            "and u.startRentDate <> :rentdate and u.startRentDate <> :endrentdate")
    boolean checkDate(@Param("id")Integer carid,@Param("rentdate")LocalDate rentdate,@Param("endrentdate")LocalDate endrentdate);

}

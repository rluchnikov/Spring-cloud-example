package java.com.github.example.state.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.com.github.example.state.entity.OrderStates;

import java.util.Optional;

@Repository
public interface OrderStatesRepository extends JpaRepository<OrderStates, Long> {

    @Query("SELECT u FROM OrderStates u WHERE u.orderId = :id")
    Optional<OrderStates> findByOrderId(@Param("id")Integer id);

   }

package java.com.github.example.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.com.github.example.order.entity.Statuses;

@Repository
public interface StateRepository extends JpaRepository<Statuses, Integer> {

    @Query("SELECT u FROM Statuses u WHERE u.name = :state")
    Statuses findByName(@Param("state")String name);

    }

package java.com.github.example.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.com.github.example.catalog.entity.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT u FROM Car u WHERE u.location.id = :id")
    List<Car> findCarsByLocation(@Param("id")Integer locationid);

    @Query("SELECT u FROM Car u WHERE u.location.id = :id and u.planned_next_state NOT BETWEEN :formDate and :endDate")
    List<Car> findCarsByLocationWithDate(@Param("id")Integer locationid,
            @Param("formDate")LocalDate formDate,
            @Param("endDate")LocalDate endDate);

    @Query("SELECT u FROM Car u WHERE u.planned_next_state BETWEEN :formDate and :endDate")
    List<Car> findAllWithDate(@Param("formDate")LocalDate formDate,
                                                @Param("endDate")LocalDate endDate);

    @Query("SELECT u FROM Car u WHERE u.state.name = :state or u.next_state.name=:state")
    List<Car> findCarsByState(@Param("state")String state);

    @Query("SELECT u FROM Car u WHERE u.state.name = :state or u.next_state.name=:state and u.planned_next_state NOT BETWEEN :formDate and :endDate")
    List<Car> findCarsByStateWithDate(@Param("state")String state,
                                             @Param("formDate")LocalDate formDate,
                                             @Param("endDate")LocalDate endDate);

    Optional<Car> findById(Integer id);
}

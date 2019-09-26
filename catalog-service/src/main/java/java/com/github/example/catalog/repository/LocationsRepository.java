package java.com.github.example.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.com.github.example.catalog.entity.Locations;

import java.util.Optional;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Integer> {

    Optional<Locations> findById(Integer id);

    @Query("SELECT u FROM Locations u WHERE u.name = :name")
    Locations findByName(@Param("name")String name);
}
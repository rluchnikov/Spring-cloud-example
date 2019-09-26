package java.com.github.example.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.com.github.example.authservice.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}

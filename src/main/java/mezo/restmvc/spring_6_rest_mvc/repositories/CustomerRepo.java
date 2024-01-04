package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {
}

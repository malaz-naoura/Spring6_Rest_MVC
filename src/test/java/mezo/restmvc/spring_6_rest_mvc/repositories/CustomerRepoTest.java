package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepoTest {
    @Autowired
    CustomerRepo customerRepo;

    @Test
    void testSave() {
        Customer customer=Customer.builder().name("customer1").build();
        Customer savedObject=customerRepo.save(customer);

//        Assertions.assertThat(savedObject).isNotNull();
//        Assertions.assertThat(savedObject.getId()).isNotNull();
    }
}
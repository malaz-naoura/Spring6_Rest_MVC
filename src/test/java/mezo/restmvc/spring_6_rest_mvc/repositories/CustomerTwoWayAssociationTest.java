package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import mezo.restmvc.spring_6_rest_mvc.entities.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerTwoWayAssociationTest implements TwoWayAssociationCheckable {
    @Autowired
    CustomerRepo customerRepo;

    void check2WayAssociation(Customer customer, Order order) {
        Assertions.assertThat(customer.getOrderList())
                  .contains(order);

        Assertions.assertThat(order.getCustomer())
                  .isEqualTo(customer);
    }


    @Test
    public void test2wayAssociationBeforePersistUsingBuilder() {
        Order order = Order.builder()
                           .customerRef("ref1")
                           .build();

        Customer customer = Customer.builder()
                                    .name("customer2")
                                    .orders(List.of(order))
                                    .build();

        check2WayAssociation(customer, order);
    }

    @Test
    public void test2wayAssociationBeforePersistUsingSetter() {
        Order order = Order.builder()
                           .customerRef("ref1")
                           .build();

        Customer customer = Customer.builder()
                                    .name("customer2")
                                    .build();

        customer.addOrder(order);
        check2WayAssociation(customer, order);
    }

    @Test
    void testSave() {
        Customer customer = Customer.builder()
                                    .name("customer1")
                                    .build();
        Customer savedObject = customerRepo.save(customer);

        Assertions.assertThat(savedObject)
                  .isNotNull();
        Assertions.assertThat(savedObject.getId())
                  .isNotNull();
    }
}
package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrderRepoTest implements TwoWayAssociationCheckable {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    CustomerRepo customerRepo;

    void check2WayAssociation(Order order,OrderShipment orderShipment,Customer customer,OrderLine orderLine) {
        // Test Customer Association
        Assertions.assertThat(order.getCustomer())
                  .isEqualTo(customer);

        Assertions.assertThat(customer.getOrderList()
                                      .contains(order))
                  .isTrue();

        // Test OrderShipment Association
        Assertions.assertThat(order.getOrderShipment())
                  .isEqualTo(orderShipment);
        Assertions.assertThat(orderShipment.getOrder())
                  .isEqualTo(order);

        // Test OrderLine Association
        Assertions.assertThat(order.getOrderLineList())
                  .contains(orderLine);
        Assertions.assertThat(orderLine.getOrder())
                  .isEqualTo(order);
    }

    @Test
    public void test2wayAssociationBeforePersistUsingBuilder() {
        Customer customer = Customer.builder()
                                    .name("customer1")
                                    .build();

        OrderShipment orderShipment = OrderShipment.builder()
                                                           .trackingNumber("123")
                                                           .build();

        OrderLine orderLine = OrderLine.builder()
                                       .orderQuantity(3)
                                       .build();

        Order order = Order.builder()
                           .customer(customer)
                           .orderShipment(orderShipment)
                           .orderLines(List.of(orderLine))
                           .build();


        check2WayAssociation(order,orderShipment,customer,orderLine);

    }

    @Test
    public void test2wayAssociationBeforePersistUsingSetter() {
        Customer customer = Customer.builder()
                                    .name("customer1")
                                    .build();

        OrderShipment orderShipment = OrderShipment.builder()
                                                   .trackingNumber("123")
                                                   .build();

        OrderLine orderLine = OrderLine.builder()
                                       .orderQuantity(3)
                                       .build();

        Order order = Order.builder()
                           .build();

        order.setCustomer(customer);
        order.setOrderShipment(orderShipment);
        order.addOrderLine(orderLine);

        check2WayAssociation(order,orderShipment,customer,orderLine);

    }
}
package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import mezo.restmvc.spring_6_rest_mvc.entities.Order;
import mezo.restmvc.spring_6_rest_mvc.entities.OrderLine;
import mezo.restmvc.spring_6_rest_mvc.entities.OrderShipment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderLineRepoTest  implements TwoWayAssociationCheckable {

    void check2WayAssociation(OrderLine orderLine, Order order) {

        Assertions.assertThat(orderLine.getOrder())
                  .isEqualTo(order);
        Assertions.assertThat(order.getOrderLineList())
                  .contains(orderLine);
    }

    @Test
    public void test2wayAssociationBeforePersistUsingBuilder() {

        Order order = Order.builder()
                           .build();

        OrderLine orderLine = OrderLine.builder()
                                       .orderQuantity(3)
                                       .order(order)
                                       .build();

        check2WayAssociation(orderLine, order);

    }

    @Test
    public void test2wayAssociationBeforePersistUsingSetter() {
        Order order = Order.builder()
                           .build();

        OrderLine orderLine = OrderLine.builder()
                                       .orderQuantity(3)
                                       .build();

        orderLine.setOrder(order);
        check2WayAssociation(orderLine, order);
    }
}
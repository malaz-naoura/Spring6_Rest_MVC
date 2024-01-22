package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Category;
import mezo.restmvc.spring_6_rest_mvc.entities.Order;
import mezo.restmvc.spring_6_rest_mvc.entities.OrderShipment;
import mezo.restmvc.spring_6_rest_mvc.entities.Product;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderShipmentRepoTest implements TwoWayAssociationCheckable {

    void check2WayAssociation(Order order, OrderShipment orderShipment) {
        Assertions.assertThat(orderShipment.getOrder())
                  .isEqualTo(order);
        Assertions.assertThat(order.getOrderShipment())
                  .isEqualTo(orderShipment);

    }

    @Test
    public void test2wayAssociationBeforePersistUsingBuilder() {
        Order order = Order.builder()
                           .customerRef("ref1")
                           .build();

        OrderShipment orderShipment = OrderShipment.builder()
                                                   .order(order)
                                                   .build();

        check2WayAssociation(order, orderShipment);

    }

    @Override
    public void test2wayAssociationBeforePersistUsingSetter() {
        Order order = Order.builder()
                           .customerRef("ref1")
                           .build();

        OrderShipment orderShipment = OrderShipment.builder()
                                                   .build();

        orderShipment.setOrder(order);
        check2WayAssociation(order, orderShipment);
    }
}
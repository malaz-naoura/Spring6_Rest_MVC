package mezo.restmvc.spring_6_rest_mvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderShipment extends BaseEntity {

    // Fields //

    @OneToOne
    private Order order;

    private String trackingNumber;

    // Methods //

    public void setOrder(final Order newOrder) {
        if (order != null && order.equals(newOrder))
            return;

        order = newOrder;
        newOrder.setOrderShipment(this);
    }

    private static final class OrderShipmentBuilderImpl extends OrderShipmentBuilder<OrderShipment, OrderShipmentBuilderImpl> {

        public OrderShipment build() {
            OrderShipment orderShipment = new OrderShipment(this);
            if (orderShipment.order != null)
                orderShipment.order.setOrderShipment(orderShipment);
            return orderShipment;
        }
    }
}

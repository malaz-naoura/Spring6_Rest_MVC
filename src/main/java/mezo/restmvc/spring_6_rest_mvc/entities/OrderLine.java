package mezo.restmvc.spring_6_rest_mvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderLine extends BaseEntity {

    // Fields //

    private Integer orderQuantity;

    @ManyToOne
    private Order order;

    // Methods //

    public void setOrder(final Order newOrder) {
        if (order != null && order.equals(newOrder))
            return;

        order = newOrder;
        newOrder.addOrderLine(this);
    }


    private static final class OrderLineBuilderImpl extends OrderLineBuilder<OrderLine, OrderLineBuilderImpl> {
        public OrderLine build() {

            OrderLine orderLine = new OrderLine(this);
            if (orderLine.order != null)
                orderLine.order.addOrderLine(orderLine);
            return orderLine;
        }
    }

}

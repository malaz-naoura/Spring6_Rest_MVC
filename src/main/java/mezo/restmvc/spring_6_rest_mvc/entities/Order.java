package mezo.restmvc.spring_6_rest_mvc.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
// https://stackoverflow.com/questions/3599803/jpa-hibernate-cant-create-entity-called-order
// The ORDER word is a reserved keyword, you have to escape it.
@Table(name = "OrderTable")
public class Order extends BaseEntity {

    // Fields //

    private String customerRef;

    @ManyToOne
    private Customer customer;


    @OneToOne(cascade = CascadeType.ALL)
    private OrderShipment orderShipment;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderLine> orderLines=new ArrayList<>();

    // Methods //

    public List<OrderLine> getOrderLineList() {
        return Collections.unmodifiableList(orderLines);
    }

    public void addOrderLine(final OrderLine newOrderLine) {
        if (orderLines.contains(newOrderLine)) return;

        orderLines.add(newOrderLine);
        newOrderLine.setOrder(this);
    }

    public void removeOrderLine(final OrderLine currOrderLine) {
        orderLines.remove(currOrderLine);
    }

    public void setCustomer(final Customer newCustomer) {
        if (customer != null && customer.equals(newCustomer)) return;

        customer = newCustomer;
        newCustomer.addOrder(this);
    }

    public void setOrderShipment(final OrderShipment newOrderShipment) {
        if (orderShipment != null && orderShipment.equals(newOrderShipment)) return;

        orderShipment = newOrderShipment;
        orderShipment.setOrder(this);
    }

    private static final class OrderBuilderImpl extends OrderBuilder<Order, OrderBuilderImpl> {
        public Order build() {

            Order order = new Order(this);
            if (order.orderShipment != null)
                order.orderShipment.setOrder(order);
            if (order.customer != null)
                order.customer.addOrder(order);
            order.orderLines.forEach(orderLine -> orderLine.setOrder(order));
            return order;
        }
    }

}

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
public class Customer extends BaseEntity {

    // Fields //

    private String name;
    private String email;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "customer",cascade = CascadeType.PERSIST)
    private List<Order> orders=new ArrayList<>();

    // Methods //

    public List<Order> getOrderList() {
        return Collections.unmodifiableList(orders);
    }

    public void addOrder(final Order newOrder) {
        if (orders.contains(newOrder))
            return;

        orders.add(newOrder);
        newOrder.setCustomer(this);
    }

    public void removeOrder(final Order currOrder) {
        orders.remove(currOrder);
    }

    private static final class CustomerBuilderImpl extends CustomerBuilder<Customer, CustomerBuilderImpl> {
        public Customer build() {
            Customer c = new Customer(this);
            c.orders.forEach(order -> order.setCustomer(c));
            return c;
        }
    }

}

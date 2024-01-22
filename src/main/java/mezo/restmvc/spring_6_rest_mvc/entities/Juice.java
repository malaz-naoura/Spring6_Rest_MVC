package mezo.restmvc.spring_6_rest_mvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Juice extends Product {

    // Fields //

    private JuiceStyle juiceStyle;
    private String upc;

    private static final class JuiceBuilderImpl extends JuiceBuilder<Juice, JuiceBuilderImpl> {

        public Juice build() {
            Juice juice= new Juice(this);
            juice.categories.forEach(category -> category.addProduct(juice));
            return juice;
        }
    }
}

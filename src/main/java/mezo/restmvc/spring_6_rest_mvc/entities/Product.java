package mezo.restmvc.spring_6_rest_mvc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product extends BaseEntity {

    // Fields //

    @NotNull
    @NotBlank
    @Size(max = 25) // validation before submit the new data to database and return exception if violate tht constraints
    @Column(length = 25) // filed's size
    protected String name;
    protected Integer quantityOnHand;
    protected BigDecimal price;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "products")
    protected List<Category> categories=new ArrayList<>();

    // Methods //

    @JsonIgnore
    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addCategory(final Category newCategory) {
        if (categories.contains(newCategory))
            return;

        categories.add(newCategory);
        newCategory.addProduct(this);

    }

    public void removeCategory(final Category currCategory) {
        categories.remove(currCategory);
    }

}

package mezo.restmvc.spring_6_rest_mvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Category extends BaseEntity {

    // Fields //

    private String description;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany
    private List<Product> products=new ArrayList<>();

    // Methods //

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(final Product newProduct) {
        if (products.contains(newProduct))
            return;

        products.add(newProduct);
        newProduct.addCategory(this);
    }

    public void removeProduct(final Product currProduct) {
        products.remove(currProduct);
    }

    private static final class CategoryBuilderImpl extends CategoryBuilder<Category, CategoryBuilderImpl> {
        public Category build() {
            Category category= new Category(this);
            category.products.forEach(product -> product.addCategory(category));
            return category;

        }
    }
}

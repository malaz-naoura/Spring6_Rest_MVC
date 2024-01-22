package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Category;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.entities.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ProdectTest implements TwoWayAssociationCheckable {


    void check2WayAssociation(Product product, Category category) {
        Assertions.assertThat(product.getCategories())
                  .contains(category);
        Assertions.assertThat(category.getProducts())
                  .contains(product);

    }

    @Test
    public void test2wayAssociationBeforePersistUsingBuilder() {

        Category category = Category.builder()
                                    .description("category_1")
                                    .build();

        Product product = Juice.builder()
                               .name("juice1")
                               .categories(List.of(category))
                               .build();


        check2WayAssociation(product, category);
    }

    @Override
    public void test2wayAssociationBeforePersistUsingSetter() {

        Category category = Category.builder()
                                    .description("category_1")
                                    .build();

        Product product = Juice.builder()
                               .name("juice1")
                               .build();

        product.addCategory(category);
        check2WayAssociation(product, category);

    }
}
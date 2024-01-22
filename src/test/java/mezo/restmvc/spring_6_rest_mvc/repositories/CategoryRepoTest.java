package mezo.restmvc.spring_6_rest_mvc.repositories;

import jakarta.transaction.Transactional;
import mezo.restmvc.spring_6_rest_mvc.entities.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class CategoryRepoTest implements TwoWayAssociationCheckable {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private JuiceRepo juiceRepo;

    void check2WayAssociation(Category category, Product product) {
        Assertions.assertThat(category.getProducts()
                                      .contains(product))
                  .isTrue();
        Assertions.assertThat(product.getCategories()
                                     .contains(category))
                  .isTrue();

    }

    @Test
    public void test2wayAssociationBeforePersistUsingBuilder() {

        Product product = Juice.builder()
                               .name("juice1")
                               .build();

        Category category = Category.builder()
                                    .description("category_1")
                                    .products(List.of(product))
                                    .build();


        check2WayAssociation(category, product);
    }

    @Override
    public void test2wayAssociationBeforePersistUsingSetter() {

        Product product = Juice.builder()
                               .name("juice1")
                               .build();

        Category category = Category.builder()
                                    .description("category_1")
                                    .build();

        category.addProduct(product);
        check2WayAssociation(category, product);

    }
}
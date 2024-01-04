package mezo.restmvc.spring_6_rest_mvc.repositories;

import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JuiceRepoTest {
    
    @Autowired
    JuiceRepo juiceRepo;

    @Test
    void testSave() {
        Juice juice=Juice.builder().juiceName("juice1").build();
        Juice savedObject=juiceRepo.save(juice);

        Assertions.assertThat(savedObject).isNotNull();
        Assertions.assertThat(savedObject.getId()).isNotNull();
    }
}
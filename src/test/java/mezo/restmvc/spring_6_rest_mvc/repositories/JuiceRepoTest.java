package mezo.restmvc.spring_6_rest_mvc.repositories;

import jakarta.validation.ConstraintViolationException;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JuiceRepoTest {
    
    @Autowired
    JuiceRepo juiceRepo;

    @Test
    void testSave() {
        Juice juice=Juice.builder().name("juice_1").build();
        Juice savedObject=juiceRepo.save(juice);

        juiceRepo.flush();

        Assertions.assertThat(savedObject).isNotNull();
        Assertions.assertThat(savedObject.getId()).isNotNull();
    }

    @Test
    void testSaveTooLongString() {

        assertThrows(ConstraintViolationException.class,() -> {
            Juice juice=Juice.builder().name("juice_1_juice_1_juice_1_juice_1_juice_1_").build();
            Juice savedObject=juiceRepo.save(juice);

            juiceRepo.flush();

            Assertions.assertThat(savedObject).isNotNull();
            Assertions.assertThat(savedObject.getId()).isNotNull();
        });

    }
}
package mezo.restmvc.spring_6_rest_mvc.bootstrap;

import jakarta.transaction.Transactional;
import mezo.restmvc.spring_6_rest_mvc.repositories.CustomerRepo;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvService;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({JuiceCsvServiceImpl.class,BootstrapData.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BootstrapDataTest {

    @Autowired
    JuiceRepo juiceRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    JuiceCsvService juiceCsvService;
    @Autowired
    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
//        bootstrapData = new BootstrapData(juiceRepo, customerRepo, juiceCsvService);
    }

    @Test
    void run() throws Exception {
        bootstrapData.run();
        Assertions.assertThat(juiceRepo.count())
                  .isGreaterThan(0);
    }
}
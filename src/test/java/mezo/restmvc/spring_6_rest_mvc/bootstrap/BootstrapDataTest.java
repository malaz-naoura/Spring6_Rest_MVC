package mezo.restmvc.spring_6_rest_mvc.bootstrap;

import mezo.restmvc.spring_6_rest_mvc.repositories.CustomerRepo;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvService;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JuiceCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    JuiceRepo juiceRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    JuiceCsvService juiceCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData=new BootstrapData(juiceRepo,customerRepo,juiceCsvService);

    }

    @Test
    void run() throws Exception {
        bootstrapData.run();
    }
}
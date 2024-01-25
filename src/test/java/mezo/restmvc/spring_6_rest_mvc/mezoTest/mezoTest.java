package mezo.restmvc.spring_6_rest_mvc.mezoTest;

import mezo.restmvc.spring_6_rest_mvc.bootstrap.BootstrapData;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import mezo.restmvc.spring_6_rest_mvc.repositories.CustomerRepo;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvService;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class mezoTest {

    @Autowired
    JuiceRepo juiceRepo;

    @Test
    void run() throws Exception {
//        var x = juiceRepo.findAll();
//        System.out.println("**********************");
//        System.out.println(x.size());
//        System.out.println("**********************");
//
//        var y = juiceRepo.findAllByJuiceStyle(JuiceStyle.valueOf("STRAWBERRY"), PageRequest.of(1, 25));
//        System.out.println("**********************");
//        System.out.println(y.getContent());
//        System.out.println("**********************");
//
//        var z1 = juiceRepo.findAllByName("Alteration", PageRequest.of(0, 25));
//        System.out.println("**********************");
//        System.out.println(z1.getContent());
//        System.out.println("**********************");
//
//        var z2 = juiceRepo.findAllByName("Alteration");
//        System.out.println("**********************");
//        System.out.println(z2);
//        System.out.println("**********************");
//
//        var z3 = juiceRepo.findByName("Alteration");
//        System.out.println("**********************");
//        System.out.println(z3);
//        System.out.println("**********************");
    }

}
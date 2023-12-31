package mezo.restmvc.spring_6_rest_mvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JuiceServiceImplTest {

    @Autowired
    JuiceServiceImpl juiceService;

    @Test
    void getJuiceById() {
        System.out.println(juiceService.getJuiceById(UUID.randomUUID()));
    }
}
package mezo.restmvc.spring_6_rest_mvc.bootstrap;

import lombok.RequiredArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import mezo.restmvc.spring_6_rest_mvc.repositories.CustomerRepo;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final JuiceRepo juiceRepo;
    private final CustomerRepo customerRepo;

    @Override
    public void run(String... args) throws Exception {
        loadJuiceData();
        loadCustomerData();
    }

    private void loadJuiceData() {
        if (juiceRepo.count() == 0){
            Juice beer1 = Juice.builder()
                               .juiceName("Galaxy Cat")
                               .juiceStyle(JuiceStyle.STRAWBERRY)
                               .upc("12356")
                               .price(new BigDecimal("12.99"))
                               .quantityOnHand(122)
                               .createdDate(LocalDateTime.now())
                               .updateDate(LocalDateTime.now())
                               .build();

            Juice beer2 = Juice.builder()
                             .juiceName("Crank")
                             .juiceStyle(JuiceStyle.LEMON)
                             .upc("12356222")
                             .price(new BigDecimal("11.99"))
                             .quantityOnHand(392)
                             .createdDate(LocalDateTime.now())
                             .updateDate(LocalDateTime.now())
                             .build();

            Juice beer3 = Juice.builder()
                             .juiceName("Sunshine City")
                             .juiceStyle(JuiceStyle.ORANGE)
                             .upc("12356")
                             .price(new BigDecimal("13.99"))
                             .quantityOnHand(144)
                             .createdDate(LocalDateTime.now())
                             .updateDate(LocalDateTime.now())
                             .build();

            juiceRepo.save(beer1);
            juiceRepo.save(beer2);
            juiceRepo.save(beer3);
        }

    }

    private void loadCustomerData() {

        if (customerRepo.count() == 0) {
            Customer customer1 = Customer.builder()
                                         .id(UUID.randomUUID())
                                         .name("Customer 1")
                                         .version(1)
                                         .createdDate(LocalDateTime.now())
                                         .updateDate(LocalDateTime.now())
                                         .build();

            Customer customer2 = Customer.builder()
                                         .id(UUID.randomUUID())
                                         .name("Customer 2")
                                         .version(1)
                                         .createdDate(LocalDateTime.now())
                                         .updateDate(LocalDateTime.now())
                                         .build();

            Customer customer3 = Customer.builder()
                                         .id(UUID.randomUUID())
                                         .name("Customer 3")
                                         .version(1)
                                         .createdDate(LocalDateTime.now())
                                         .updateDate(LocalDateTime.now())
                                         .build();

            customerRepo.saveAll(Arrays.asList(customer1, customer2, customer3));
        }

    }

}

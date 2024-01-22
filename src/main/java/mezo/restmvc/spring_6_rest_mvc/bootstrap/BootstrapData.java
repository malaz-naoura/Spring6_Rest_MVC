package mezo.restmvc.spring_6_rest_mvc.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.entities.Customer;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.mezoutils.Random;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceCsvRecord;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import mezo.restmvc.spring_6_rest_mvc.repositories.CustomerRepo;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceCsvService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final JuiceRepo juiceRepo;
    private final CustomerRepo customerRepo;
    private final JuiceCsvService juiceCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
//        loadJuiceData();
        loadCustomerData();
        loadJuiceFromCsvData();
    }

    private void loadJuiceData() {
        if (juiceRepo.count() == 0) {
            Juice beer1 = Juice.builder()
                               .name("Galaxy Cat")
                               .juiceStyle(JuiceStyle.STRAWBERRY)
                               .upc("12356")
                               .price(new BigDecimal("12.99"))
                               .quantityOnHand(122)
                               .createdDate(LocalDateTime.now())
                               .updateDate(LocalDateTime.now())
                               .build();

            Juice beer2 = Juice.builder()
                               .name("Crank")
                               .juiceStyle(JuiceStyle.LEMON)
                               .upc("12356222")
                               .price(new BigDecimal("11.99"))
                               .quantityOnHand(392)
                               .createdDate(LocalDateTime.now())
                               .updateDate(LocalDateTime.now())
                               .build();

            Juice beer3 = Juice.builder()
                               .name("Sunshine City")
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

    private void loadJuiceFromCsvData() throws IOException {


//        URL currentFile = ResourceUtils.getURL("classpath:csvData/Juices.csv");

//        ClassPathResource classPathResource= new ClassPathResource("classpath:csvData/Juices.csv");
//        File currentFile= classPathResource.getFile();

//        Resource classPathResource = resourceLoader.getResource("classpath:csvData/Juices.csv");
//        File currentFile=classPathResource.getFile();

//        Resource resource = new ClassPathResource("Juices.csv");
//        File currentFile = resource.getFile();

        //todo
        //all above way the same problem as below
        //when package the jar and try to run it will throw exciton with this message: Caused by: java.io.FileNotFoundException: class path resource [Juices.csv] cannot be resolved to absolute file path because it does not reside in the file system: jar:nested:/C:/Users/M1/.m2/repository/mezo/restmvc/Spring_6_Rest_MVC/0.0.1-SNAPSHOT/Spring_6_Rest_MVC-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/Juices.csv
        //don't forget to change in JuiceCsvServiceTest.convertCsv when change here
        File currentFile = ResourceUtils.getFile("classpath:csvData/Juices.csv");

        List<JuiceCsvRecord> juiceCsvRecords = juiceCsvService.convertCsv(currentFile);

        if (juiceRepo.count() > 0)
            return;

        juiceRepo.saveAll(juiceCsvRecords.stream()
                                         .map(o -> {
                                             return Juice.builder()
                                                         .name(StringUtils.abbreviate(o.getJuice(), 10))
                                                         .quantityOnHand(Integer.valueOf(o.getCount_y()))
                                                         .juiceStyle(Random.getRandomValueOf(JuiceStyle.class))
                                                         .price(BigDecimal.valueOf(
                                                                 new java.util.Random().nextInt(1000)))
                                                         .build();
                                         })
                                         .collect(Collectors.toList()));
    }
}

package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.JuiceCsvRecord;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class JuiceCsvServiceTest {

    JuiceCsvService juiceCsvService = new JuiceCsvServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {

        File csvFile = ResourceUtils.getFile("classpath:csvData/Juices.csv");
        List<JuiceCsvRecord> juiceCsvRecords = juiceCsvService.convertCsv(csvFile);
        assertThat(juiceCsvRecords.size()).isGreaterThan(0);

    }
}
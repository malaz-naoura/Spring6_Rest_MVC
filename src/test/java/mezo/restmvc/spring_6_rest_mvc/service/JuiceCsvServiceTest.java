package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.JuiceCsvRecord;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class JuiceCsvServiceTest {

    JuiceCsvService juiceCsvService = new JuiceCsvServiceImpl();

    @Test
    void convertCsv() throws IOException {

//        URL csvFile = ResourceUtils.getURL("classpath:csvData/Juices.csv");
//        ClassPathResource classPathResource= new ClassPathResource("classpath:csvData/Juices.csv");
//        File csvFile= classPathResource.getFile();

//        Resource resource = new ClassPathResource("csvData/Juices.csv");
//        File csvFile = resource.getFile();

        File csvFile = ResourceUtils.getFile("classpath:csvData/Juices.csv");


        List<JuiceCsvRecord> juiceCsvRecords = juiceCsvService.convertCsv(csvFile);
        assertThat(juiceCsvRecords.size()).isGreaterThan(0);

    }
}
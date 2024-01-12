package mezo.restmvc.spring_6_rest_mvc.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceCsvRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JuiceCsvServiceImpl implements JuiceCsvService {

    @Override
    public List<JuiceCsvRecord> convertCsv(File csvFile) throws FileNotFoundException {

        CsvToBean csvToBean = new CsvToBeanBuilder<JuiceCsvRecord>(new FileReader(csvFile)).withType(JuiceCsvRecord.class)
                                                                                           .build();

        List<JuiceCsvRecord> juiceCsvRecords = (List<JuiceCsvRecord>) csvToBean.parse()
                                                                               .stream()
                                                                               .filter(o -> o instanceof JuiceCsvRecord)
                                                                               .collect(Collectors.toList());

        return juiceCsvRecords;
    }
}

package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.JuiceCsvRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

public interface JuiceCsvService {
    List<JuiceCsvRecord> convertCsv(File csvFile) throws FileNotFoundException;
}

package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.Juice;

import java.util.UUID;

public interface JuiceService {
    Juice getJuiceById(UUID id);
}

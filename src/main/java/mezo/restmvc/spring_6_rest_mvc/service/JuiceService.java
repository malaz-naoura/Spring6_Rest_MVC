package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.Juice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JuiceService {
    List<Juice> listJuices();

    Optional<Juice> getJuiceById(UUID id);

    Juice addJuice(Juice juice);

    Juice updateOrCreate(UUID id, Juice juice);

    void removeById(UUID id);

    void update(UUID id, Juice juice);
}

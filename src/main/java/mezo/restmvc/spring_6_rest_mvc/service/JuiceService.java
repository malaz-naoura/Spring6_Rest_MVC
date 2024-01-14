package mezo.restmvc.spring_6_rest_mvc.service;

import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JuiceService {
    List<JuiceDTO> listJuices(String juiceName,String juiceStyle,Boolean showInventory);

    Optional<JuiceDTO> getJuiceById(UUID id);

    JuiceDTO addJuice(JuiceDTO juiceDTO);

    JuiceDTO updateOrCreate(UUID id, JuiceDTO juiceDTO);

    Boolean removeById(UUID id);

    Optional<JuiceDTO> update(UUID id, JuiceDTO juiceDTO);
}

package mezo.restmvc.spring_6_rest_mvc.service;

import lombok.extern.slf4j.Slf4j;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class JuiceServiceImpl implements JuiceService {

    Map<UUID, JuiceDTO> uuidJuiceMap;

    public JuiceServiceImpl() {
        this.uuidJuiceMap = new HashMap<>();
        JuiceDTO juiceDTO1 = JuiceDTO.builder()
                                     .id(UUID.randomUUID())
                                     .juiceName("juice one")
                                     .createdDate(LocalDateTime.now())
                                     .build();

        JuiceDTO juiceDTO2 = JuiceDTO.builder()
                                     .id(UUID.randomUUID())
                                     .juiceName("juice two")
                                     .createdDate(LocalDateTime.now())
                                     .build();

        JuiceDTO juiceDTO3 = JuiceDTO.builder()
                                     .id(UUID.randomUUID())
                                     .juiceName("juice third")
                                     .createdDate(LocalDateTime.now())
                                     .build();

        this.uuidJuiceMap.put(juiceDTO1.getId(), juiceDTO1);
        this.uuidJuiceMap.put(juiceDTO2.getId(), juiceDTO2);
        this.uuidJuiceMap.put(juiceDTO3.getId(), juiceDTO3);
    }

    @Override
    public List<JuiceDTO> listJuices(String juiceName, String juiceStyle, Boolean showInventory) {
        return uuidJuiceMap.values()
                           .stream()
                           .toList();
    }

    @Override
    public Optional<JuiceDTO> getJuiceById(UUID id) {
        log.debug("getJuiceById - JuiceService");
        return Optional.of(this.uuidJuiceMap.get(id));
    }

    @Override
    public JuiceDTO addJuice(JuiceDTO juiceDTO) {
        UUID id = UUID.randomUUID();
        juiceDTO.setId(id);
        uuidJuiceMap.put(id, juiceDTO);
        return uuidJuiceMap.get(id);
    }

    @Override
    public JuiceDTO updateOrCreate(UUID id, JuiceDTO juiceDTO) {
        uuidJuiceMap.put(id, juiceDTO);
        return uuidJuiceMap.get(id);
    }

    @Override
    public Boolean removeById(UUID id) {
        if (!uuidJuiceMap.containsKey(id))
            return false;
        uuidJuiceMap.remove(id);
        return true;
    }

    @Override
    public Optional<JuiceDTO> update(UUID id, JuiceDTO newJuiceDTO) {
        JuiceDTO toUpdateJuiceDTO = uuidJuiceMap.get(id);
        if (StringUtils.hasText(newJuiceDTO.getJuiceName())) {
            toUpdateJuiceDTO.setJuiceName(newJuiceDTO.getJuiceName());
        }
        if (newJuiceDTO.getPrice() != (null)) {
            toUpdateJuiceDTO.setPrice(newJuiceDTO.getPrice());
        }
        // continue to all properties like this
        // if it's a string data type check whether exist and if it's object check is snot null

        uuidJuiceMap.put(id, toUpdateJuiceDTO);

        return Optional.of(toUpdateJuiceDTO);
    }
}


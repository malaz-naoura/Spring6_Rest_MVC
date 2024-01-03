package mezo.restmvc.spring_6_rest_mvc.service;

import lombok.extern.slf4j.Slf4j;
import mezo.restmvc.spring_6_rest_mvc.model.Juice;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class JuiceServiceImpl implements JuiceService {

    Map<UUID, Juice> uuidJuiceMap;

    public JuiceServiceImpl() {
        this.uuidJuiceMap = new HashMap<>();
        Juice juice1 = Juice.builder()
                            .id(UUID.randomUUID())
                            .juiceName("juice one")
                            .createdDate(LocalDateTime.now())
                            .build();

        Juice juice2 = Juice.builder()
                            .id(UUID.randomUUID())
                            .juiceName("juice two")
                            .createdDate(LocalDateTime.now())
                            .build();

        Juice juice3 = Juice.builder()
                            .id(UUID.randomUUID())
                            .juiceName("juice third")
                            .createdDate(LocalDateTime.now())
                            .build();

        this.uuidJuiceMap.put(juice1.getId(), juice1);
        this.uuidJuiceMap.put(juice2.getId(), juice2);
        this.uuidJuiceMap.put(juice3.getId(), juice3);
    }

    @Override
    public List<Juice> listJuices() {
        return uuidJuiceMap.values()
                           .stream()
                           .toList();
    }

    @Override
    public Optional<Juice> getJuiceById(UUID id) {
        log.debug("getJuiceById - JuiceService");
        return Optional.of(this.uuidJuiceMap.get(id));
    }

    @Override
    public Juice addJuice(Juice juice) {
        UUID id = UUID.randomUUID();
        juice.setId(id);
        uuidJuiceMap.put(id, juice);
        return uuidJuiceMap.get(id);
    }

    @Override
    public Juice updateOrCreate(UUID id, Juice juice) {
        uuidJuiceMap.put(id, juice);
        return uuidJuiceMap.get(id);
    }

    @Override
    public void removeById(UUID id) {
        uuidJuiceMap.remove(id);
    }

    @Override
    public void update(UUID id, Juice newJuice) {
        Juice toUpdateJuice = uuidJuiceMap.get(id);
        if (StringUtils.hasText(newJuice.getJuiceName())) {
            toUpdateJuice.setJuiceName(newJuice.getJuiceName());
        }
        if (newJuice.getPrice() != (null)) {
            toUpdateJuice.setPrice(newJuice.getPrice());
        }
        // continue to all properties like this
        // if it's a string data type check whether exist and if it's object check is snot null

        uuidJuiceMap.put(id, toUpdateJuice);
    }
}


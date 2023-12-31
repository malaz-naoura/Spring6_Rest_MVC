package mezo.restmvc.spring_6_rest_mvc.service;

import lombok.extern.slf4j.Slf4j;
import mezo.restmvc.spring_6_rest_mvc.model.Juice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class JuiceServiceImpl implements JuiceService {
    @Override
    public Juice getJuiceById(UUID id) {

        log.debug("getJuiceById - JuiceService");

        return Juice.builder()
                    .id(id)
                    .juiceName("chockmock")
                    .createdDate(LocalDateTime.now())
                    .build();
    }
}


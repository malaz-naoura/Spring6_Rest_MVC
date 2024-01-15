package mezo.restmvc.spring_6_rest_mvc.service;

import lombok.RequiredArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.mappers.JuiceMapper;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Primary
public class JuiceServiceJPAImpl implements JuiceService {

    private final JuiceRepo juiceRepo;
    private final JuiceMapper juiceMapper;

    List<Juice> listJuiceByName(String juiceName) {
        return juiceRepo.findAllByJuiceName(juiceName);
    }

    List<Juice> listJuiceByStyle(String juiceStyle) {
        return juiceRepo.findAllByJuiceStyle(JuiceStyle.valueOf(juiceStyle));
    }

    List<Juice> listJuiceByNameAndStyle(String juiceName, String juiceStyle) {
        return juiceRepo.findAllByJuiceNameAndJuiceStyle(juiceName, JuiceStyle.valueOf(juiceStyle));
    }

    @Override
    public List<JuiceDTO> listJuices(String juiceName, String juiceStyle, Boolean showInventory) {

        List<Juice> juiceList = null;

        if (StringUtils.hasText(juiceName) && StringUtils.hasText(juiceStyle)) {
            juiceList = listJuiceByNameAndStyle(juiceName, juiceStyle);

        } else if (StringUtils.hasText(juiceName)) {
            juiceList = listJuiceByName(juiceName);

        } else if (StringUtils.hasText(juiceStyle)) {
            juiceList = listJuiceByStyle(juiceStyle);

        } else {
            juiceList = juiceRepo.findAll();
        }

        if(showInventory==null || !showInventory){
            juiceList.forEach(juice -> juice.setQuantityOnHand(null));
        }

        return juiceList.stream()
                        .map(juiceMapper::objToDto)
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<JuiceDTO> getJuiceById(UUID id) {
        return Optional.ofNullable(juiceMapper.objToDto(juiceRepo.findById(id)
                                                                 .orElse(null)));
    }

    @Override
    public JuiceDTO addJuice(JuiceDTO juiceDTO) {
        Juice juice = juiceMapper.dtoToObj(juiceDTO);
        return juiceMapper.objToDto(juiceRepo.save(juice));
    }

    @Override
    public JuiceDTO updateOrCreate(UUID id, JuiceDTO juiceDTO) {
        return null;
    }

    @Override
    public Boolean removeById(UUID id) {
        if (juiceRepo.existsById(id)) {
            juiceRepo.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public Optional<JuiceDTO> update(UUID id, JuiceDTO juiceDTO) {

        AtomicReference<Optional<JuiceDTO>> atomicReference = new AtomicReference<>();

        juiceRepo.findById(id)
                 .ifPresentOrElse(juice -> {
                     if (juiceDTO.getId() != null) juice.setJuiceName(juiceDTO.getJuiceName());

                     if (juiceDTO.getJuiceName() != null) juice.setJuiceName(juiceDTO.getJuiceName());

                     if (juiceDTO.getJuiceStyle() != null) juice.setJuiceStyle(juiceDTO.getJuiceStyle());

                     if (juiceDTO.getPrice() != null) juice.setPrice(juiceDTO.getPrice());

                     atomicReference.set(Optional.of(juiceMapper.objToDto(juice)));
                     juiceRepo.save(juice);
                 }, () -> {
                     atomicReference.set(Optional.empty());
                 });

        return atomicReference.get();
    }
}

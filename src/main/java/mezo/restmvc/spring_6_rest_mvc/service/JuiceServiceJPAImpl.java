package mezo.restmvc.spring_6_rest_mvc.service;

import lombok.RequiredArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.mappers.JuiceMapper;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceStyle;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
@Primary
public class JuiceServiceJPAImpl implements JuiceService {

    private final JuiceRepo juiceRepo;
    private final JuiceMapper juiceMapper;

    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 25;

    PageRequest createNewPageRequest(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) pageNumber = DEFAULT_PAGE_NUMBER;

        if (pageSize == null || pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
        else if (pageSize > 1000) pageSize = 1000;

        String filedNameToSortElementBy = "name";
        List<Field> fileds = new ArrayList<>();

        Class temp = Juice.class;
        while (temp != null) {
            fileds.addAll(Arrays.stream(temp.getDeclaredFields())
                                .filter(field -> Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(
                                        field.getModifiers()))
                                .toList());
            temp = temp.getSuperclass();
        }

        Boolean isExistField = fileds.stream()
                                     .anyMatch(field -> field.getName()
                                                             .equals(filedNameToSortElementBy));

        if (!isExistField)
            throw new RuntimeException("No field with name of (" + filedNameToSortElementBy + ") is founded");

        Sort sort = Sort.by(filedNameToSortElementBy);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    Page<Juice> listJuiceByName(String juiceName, Pageable pageable) {
        return juiceRepo.findAllByName(juiceName, pageable);
    }

    Page<Juice> listJuiceByStyle(String juiceStyle, Pageable pageable) {
        return juiceRepo.findAllByJuiceStyle(JuiceStyle.valueOf(juiceStyle), pageable);
    }

    Page<Juice> listJuiceByNameAndStyle(String juiceName, String juiceStyle, Pageable pageable) {
        return juiceRepo.findAllByNameAndJuiceStyle(juiceName, JuiceStyle.valueOf(juiceStyle), pageable);
    }

    @Override
    public Page<JuiceDTO> listJuices(String juiceName, String juiceStyle, Boolean showInventory, Integer pageNumber,
                                     Integer pageSize) {

        PageRequest pageRequest = createNewPageRequest(pageNumber, pageSize);

        Page<Juice> juiceList = null;

        if (StringUtils.hasText(juiceName) && StringUtils.hasText(juiceStyle)) {
            juiceList = listJuiceByNameAndStyle(juiceName, juiceStyle, pageRequest);

        } else if (StringUtils.hasText(juiceName)) {
            juiceList = listJuiceByName(juiceName, pageRequest);

        } else if (StringUtils.hasText(juiceStyle)) {
            juiceList = listJuiceByStyle(juiceStyle, pageRequest);

        } else {
            juiceList = juiceRepo.findAll(pageRequest);
        }

        if (showInventory == null || !showInventory) {
            juiceList.forEach(juice -> juice.setQuantityOnHand(null));
        }

        return juiceList.map(juiceMapper::objToDto);
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

        AtomicReference<JuiceDTO> atomicReference=new AtomicReference<>();

        juiceRepo.findById(id).ifPresentOrElse(juice -> {
            juice.setName(juiceDTO.getName());
            juice.setJuiceStyle(juiceDTO.getJuiceStyle());
            juice.setPrice(juiceDTO.getPrice());
            juice.setQuantityOnHand(juiceDTO.getQuantityOnHand());
            juice.setUpc(juiceDTO.getUpc());

            atomicReference.set(juiceMapper.objToDto(juice));
            juiceRepo.save(juice);

        },() -> {
             Juice createJuice= juiceRepo.save(juiceMapper.dtoToObj(juiceDTO));
            atomicReference.set(juiceMapper.objToDto(createJuice));
        });

        return atomicReference.get();
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
                     if (juiceDTO.getName() != null) juice.setName(juiceDTO.getName());

                     if (juiceDTO.getQuantityOnHand() != null) juice.setQuantityOnHand(juiceDTO.getQuantityOnHand());

                     if (juiceDTO.getUpc() != null) juice.setUpc(juiceDTO.getUpc());

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

package mezo.restmvc.spring_6_rest_mvc.controllers;

import jakarta.transaction.Transactional;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.mappers.JuiceMapper;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class JuiceControllerIT {

    @Autowired
    JuiceController juiceController;

    @Autowired
    JuiceRepo juiceRepo;

    @Autowired
    JuiceMapper juiceMapper;

    @Test
    void testListJuice() {
        List<JuiceDTO> juiceDTOList = juiceController.listJuice();
        Assertions.assertThat(juiceDTOList.size())
                  .isEqualTo(juiceRepo.count());
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyListJuice() {
        juiceRepo.deleteAll();
        List<JuiceDTO> juiceDTOList = juiceController.listJuice();
        Assertions.assertThat(juiceDTOList.size())
                  .isEqualTo(juiceRepo.count());
    }

    @Test
    void testGetById() {
        Juice actual = juiceRepo.findAll()
                                .get(0);

        JuiceDTO expected = juiceController.getById(actual.getId());

        Assertions.assertThat(actual.getId())
                  .isEqualTo(expected.getId());
    }

    @Test
    void testNotFoundIdExceptions() {
        assertThrows(NotFoundException.class, () -> {
            juiceController.getById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testCreateNewObject() {
        Juice newJuice = Juice.builder()
                              .juiceName("juiceee")
                              .build();

        ResponseEntity responseEntity = juiceController.saveNewJuice(juiceMapper.objToDto(newJuice));

        Assertions.assertThat(newJuice.getJuiceName())
                  .isEqualTo(juiceRepo.findAll()
                                      .stream()
                                      .filter(juice -> juice.getJuiceName()
                                                            .equals(newJuice.getJuiceName()))
                                      .findFirst()
                                      .get()
                                      .getJuiceName());

        Assertions.assertThat(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                  .isEqualTo(responseEntity.getStatusCode());

    }

    @Transactional
    @Rollback
    @Test
    void testUpdateById() {
        Juice juice = juiceRepo.findAll()
                               .get(0);

        JuiceDTO juiceDTO = juiceMapper.objToDto(juice);
        juiceDTO.setId(null);
        juiceDTO.setVersion(null);
        juiceDTO.setJuiceName(juiceDTO.getJuiceName() + " updated");

        ResponseEntity responseEntity = juiceController.updateJuice(juice.getId(), juiceDTO);


        Juice updatedJuice = juiceRepo.findById(juice.getId())
                                      .get();

        Assertions.assertThat(juiceDTO.getJuiceName())
                  .isEqualTo(updatedJuice.getJuiceName());

        Assertions.assertThat(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()))
                  .isEqualTo(responseEntity.getStatusCode());
    }

    @Test
    void testUpdateByNotFoundId() {
        assertThrows(NotFoundException.class, () -> {
            juiceController.updateJuice(UUID.randomUUID(), JuiceDTO.builder()
                                                                   .build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteById() {
        Long oldSize = juiceRepo.count();
        UUID id = juiceRepo.findAll()
                           .get(0)
                           .getId();
        ResponseEntity responseEntity = juiceController.deleteById(id);
        Long newSize = juiceRepo.count();


        Assertions.assertThat(oldSize)
                  .isEqualTo(newSize + 1);
        Assertions.assertThat(juiceRepo.findById(id)
                                       .isEmpty());
        Assertions.assertThat(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()))
                  .isEqualTo(responseEntity.getStatusCode());
    }

    @Test
    void testDeleteByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            juiceController.deleteById(UUID.randomUUID());
        });

    }

}
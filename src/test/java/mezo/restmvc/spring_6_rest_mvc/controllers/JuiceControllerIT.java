package mezo.restmvc.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.mappers.JuiceMapper;
import mezo.restmvc.spring_6_rest_mvc.mezoutils.Random;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
import mezo.restmvc.spring_6_rest_mvc.repositories.JuiceRepo;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceServiceJPAImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class JuiceControllerIT {

    @Autowired
    JuiceController juiceController;

    @Autowired
    JuiceRepo juiceRepo;

    @Autowired
    JuiceMapper juiceMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .build();
    }

    @Test
    void testListJuice() {
        Page<JuiceDTO> juiceDTOList = juiceController.listJuice(null, null, null, null, null);
        Assertions.assertThat(jsonPath("$.totalElements", is(juiceRepo.count())));
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyListJuice() {
        juiceRepo.deleteAll();
        Page<JuiceDTO> juiceDTOList = juiceController.listJuice(null, null, null, null, null);
        Assertions.assertThat(jsonPath("$.totalElements", is(juiceRepo.count())));
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

    @Test
    void updateJuiceWithTooLongJuiceName() throws Exception {

        Juice juice = juiceRepo.findAll()
                               .get(0);

        juice.setJuiceName("mezoooo1234567890mezoooo1234567890mezoooo1234567890");

        MvcResult mvcResult = mockMvc.perform(
                                             patch(JuiceController.JUICE_PATH_ID, juice.getId()).accept(MediaType.APPLICATION_JSON)
                                                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                                                .content(objectMapper.writeValueAsString(juice)))
                                     .andExpect(status().isBadRequest())
                                     .andReturn();

        System.out.println(mvcResult.getResponse()
                                    .getContentAsString());
    }

    @Test
    void testListJuiceByName() throws Exception {

        Juice juice = Random.getRandomValueOf(juiceRepo.findAll());
        String juiceName = juice.getJuiceName();


        Integer sizeOfList = juiceRepo.findAllByJuiceName(juiceName, null)
                                      .getContent()
                                      .size();

        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("juiceName", juiceName))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.totalElements", is(sizeOfList)))
               .andExpect(jsonPath("$.*.['juiceName']", everyItem(is(juiceName))));
    }

    @Test
    void testListJuiceByJuiceStyle() throws Exception {
        Juice juice = Random.getRandomValueOf(juiceRepo.findAll());
        String juiceStyleString = juice.getJuiceStyle()
                                       .name();

        Integer sizeOfList = juiceRepo.findAllByJuiceStyle(juice.getJuiceStyle(), null)
                                      .getContent()
                                      .size();

        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("juiceStyle", juiceStyleString))
               .andExpect(jsonPath("$.totalElements", is(sizeOfList)))
               .andExpect(jsonPath("$.*.['juiceStyle']", everyItem(is(juiceStyleString))));

    }

    @Test
    void testListJuiceByNameAndJuiceStyle() throws Exception {
        Juice juice = Random.getRandomValueOf(juiceRepo.findAll());
        String juiceName = juice.getJuiceName();
        String juiceStyleString = juice.getJuiceStyle()
                                       .name();

        Integer sizeOfList = juiceRepo.findAllByJuiceNameAndJuiceStyle(juiceName, juice.getJuiceStyle(), null)
                                      .getContent()
                                      .size();

        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("juiceName", juiceName)
                                                       .queryParam("juiceStyle", juiceStyleString))
               .andExpect(jsonPath("$.totalElements", is(sizeOfList)))
               .andExpect(jsonPath("$.*.['juiceName']", everyItem(is(juiceName))))
               .andExpect(jsonPath("$.*.['juiceStyle']", everyItem(is(juiceStyleString))));

    }

    @Test
    void testListJuiceByShowInventoryTrue() throws Exception {
        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("showInventory", String.valueOf(true)))
               .andExpect(jsonPath("$.*.['quantityOnHand']", everyItem(notNullValue())));

    }

    @Test
    void testListJuiceByShowInventoryFalse() throws Exception {
        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("showInventory", String.valueOf(false)))
               .andExpect(jsonPath("$.*.['quantityOnHand']", everyItem(nullValue())));

    }

    @Test
    void testListJuiceByNameAndJuiceStyleAndShowInventoryTrue() throws Exception {
        Juice juice = Random.getRandomValueOf(juiceRepo.findAll());
        String juiceName = juice.getJuiceName();
        String juiceStyleString = juice.getJuiceStyle()
                                       .name();

        Integer sizeOfList = juiceRepo.findAllByJuiceNameAndJuiceStyle(juiceName, juice.getJuiceStyle(), null)
                                      .getContent()
                                      .size();

        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("juiceName", juiceName)
                                                       .queryParam("juiceStyle", juiceStyleString)
                                                       .queryParam("showInventory", String.valueOf(true)))
               .andExpect(jsonPath("$.totalElements", is(sizeOfList)))
               .andExpect(jsonPath("$.*.['juiceName']", everyItem(is(juiceName))))
               .andExpect(jsonPath("$.*.['juiceStyle']", everyItem(is(juiceStyleString))))
               .andExpect(jsonPath("$.*.['quantityOnHand']", everyItem(notNullValue())));

    }

    @Test
    void testListJuiceByNameAndJuiceStyleAndShowInventoryFalse() throws Exception {
        Juice juice = Random.getRandomValueOf(juiceRepo.findAll());
        String juiceName = juice.getJuiceName();
        String juiceStyleString = juice.getJuiceStyle()
                                       .name();

        Integer sizeOfList = juiceRepo.findAllByJuiceNameAndJuiceStyle(juiceName, juice.getJuiceStyle(), null)
                                      .getContent()
                                      .size();

        mockMvc.perform(get(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                       .queryParam("juiceName", juiceName)
                                                       .queryParam("juiceStyle", juiceStyleString)
                                                       .queryParam("showInventory", String.valueOf(false)))
               .andExpect(jsonPath("$.totalElements", is(sizeOfList)))
               .andExpect(jsonPath("$.*.['juiceName']", everyItem(is(juiceName))))
               .andExpect(jsonPath("$.*.['juiceStyle']", everyItem(is(juiceStyleString))))
               .andExpect(jsonPath("$.*.['quantityOnHand']", everyItem(nullValue())));
    }



}
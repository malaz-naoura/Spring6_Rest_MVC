package mezo.restmvc.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mezo.restmvc.spring_6_rest_mvc.model.Juice;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceService;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(JuiceController.class)
class JuiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JuiceService juiceService;

    JuiceServiceImpl juiceServiceImpl;

    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

    ArgumentCaptor<Juice> juiceArgumentCaptor = ArgumentCaptor.forClass(Juice.class);


    @BeforeEach
    void setUp() {
        juiceServiceImpl = new JuiceServiceImpl();
    }

    @Test
    void listJuice() throws Exception {
        List<Juice> juiceList = juiceServiceImpl.listJuices();

        given(juiceService.listJuices()).willReturn(juiceList);

        mockMvc.perform(get(JuiceController.JUICE_PATH).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()", is(juiceList.size())));
    }

    @Test
    void getById() throws Exception {
        Juice juice = juiceServiceImpl.listJuices()
                                      .get(0);

        given(juiceService.getJuiceById(any(UUID.class))).willReturn(Optional.of(juice));

        mockMvc.perform(get(JuiceController.JUICE_PATH_ID, juice.getId()).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               // throw exception when using  optional class and jsonPath together, because the different type of UUID detected
//               .andExpect(jsonPath("$.id", is(Optional.of(juice).get().getId())))
               .andExpect(jsonPath("$.juiceName", is(juice.getJuiceName())));
    }

    @Test
    void saveNewJuice() throws Exception {
        Integer lastElement = juiceServiceImpl.listJuices()
                                              .size() - 1;
        Juice originalJuice = juiceServiceImpl.listJuices()
                                              .get(lastElement);

        given(juiceService.addJuice(any(Juice.class))).willReturn(originalJuice);

        Juice newJuice = originalJuice.toBuilder()
                                      .id(null)
                                      .version(null)
                                      .build();
        newJuice.setId(null);
        newJuice.setVersion(null);

        mockMvc.perform(post(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(newJuice))
                                                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"));
    }

    @Test
    void updateOrCreateJuice() throws Exception {

        Juice juice = juiceServiceImpl.listJuices()
                                      .get(0);

        mockMvc.perform(put(JuiceController.JUICE_PATH_ID, juice.getId()).contentType(MediaType.APPLICATION_JSON)
                                                                         .content(objectMapper.writeValueAsString(juice))
                                                                         .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(juiceService).updateOrCreate(any(UUID.class), any(Juice.class));
    }

    @Test
    void deleteById() throws Exception {

        Juice juice = juiceServiceImpl.listJuices()
                                      .get(0);

        mockMvc.perform(delete(JuiceController.JUICE_PATH_ID, juice.getId()).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(juiceService).removeById(uuidArgumentCaptor.capture());

        assertThat(juice.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void updateJuice() throws Exception {
        Juice juice = juiceServiceImpl.listJuices()
                                      .get(0);

        juice.setJuiceName("mezoooo");

        mockMvc.perform(patch(JuiceController.JUICE_PATH_ID, juice.getId()).accept(MediaType.APPLICATION_JSON)
                                                                           .contentType(MediaType.APPLICATION_JSON)
                                                                           .content(objectMapper.writeValueAsString(juice)))
               .andExpect(status().isNoContent());

        verify(juiceService).update(uuidArgumentCaptor.capture(), juiceArgumentCaptor.capture());

        assertThat(juice.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(juice.getJuiceName()).isEqualTo(juiceArgumentCaptor.getValue()
                                                                      .getJuiceName());
    }

    @Test
    void getJuiceByIdNotFound() throws Exception {

        given(juiceService.getJuiceById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(JuiceController.JUICE_PATH_ID, UUID.randomUUID()))
               .andExpect(status().isNotFound());
    }
}
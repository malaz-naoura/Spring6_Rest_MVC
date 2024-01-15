package mezo.restmvc.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mezo.restmvc.spring_6_rest_mvc.entities.Juice;
import mezo.restmvc.spring_6_rest_mvc.model.JuiceDTO;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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

    ArgumentCaptor<JuiceDTO> juiceArgumentCaptor = ArgumentCaptor.forClass(JuiceDTO.class);


    @BeforeEach
    void setUp() {
        juiceServiceImpl = new JuiceServiceImpl();
    }

    @Test
    void listJuice() throws Exception {
        List<JuiceDTO> juiceDTOList = juiceServiceImpl.listJuices(null,null,null);

        given(juiceService.listJuices(any(),any(),any())).willReturn(juiceDTOList);

        mockMvc.perform(get(JuiceController.JUICE_PATH).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()", is(juiceDTOList.size())));
    }

    @Test
    void getById() throws Exception {
        JuiceDTO juiceDTO = juiceServiceImpl.listJuices(null,null,null)
                                            .get(0);

        given(juiceService.getJuiceById(any(UUID.class))).willReturn(Optional.of(juiceDTO));

        mockMvc.perform(get(JuiceController.JUICE_PATH_ID, juiceDTO.getId()).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               // throw exception when using  optional class and jsonPath together, because the different type of UUID detected
//               .andExpect(jsonPath("$.id", is(Optional.of(juice).get().getId())))
               .andExpect(jsonPath("$.juiceName", is(juiceDTO.getJuiceName())));
    }

    @Test
    void saveNewJuice() throws Exception {
        Integer lastElement = juiceServiceImpl.listJuices(null,null,null)
                                              .size() - 1;
        JuiceDTO originalJuiceDTO = juiceServiceImpl.listJuices(null,null,null)
                                                    .get(lastElement);

        given(juiceService.addJuice(any(JuiceDTO.class))).willReturn(originalJuiceDTO);

        JuiceDTO newJuiceDTO = originalJuiceDTO.toBuilder()
                                               .id(null)
                                               .version(null)
                                               .build();
        newJuiceDTO.setId(null);
        newJuiceDTO.setVersion(null);

        mockMvc.perform(post(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(newJuiceDTO))
                                                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"));
    }

    @Test
    void updateOrCreateJuice() throws Exception {

        JuiceDTO juiceDTO = juiceServiceImpl.listJuices(null,null,null)
                                            .get(0);

        mockMvc.perform(put(JuiceController.JUICE_PATH_ID, juiceDTO.getId()).contentType(MediaType.APPLICATION_JSON)
                                                                            .content(objectMapper.writeValueAsString(juiceDTO))
                                                                            .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(juiceService).updateOrCreate(any(UUID.class), any(JuiceDTO.class));
    }

    @Test
    void deleteById() throws Exception {

        JuiceDTO juiceDTO = juiceServiceImpl.listJuices(null,null,null)
                                            .get(0);
        given(juiceService.removeById(any(UUID.class))).willReturn(Boolean.TRUE);

        mockMvc.perform(delete(JuiceController.JUICE_PATH_ID, juiceDTO.getId()).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(juiceService).removeById(uuidArgumentCaptor.capture());

        assertThat(juiceDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void updateJuice() throws Exception {
        JuiceDTO juiceDTO = juiceServiceImpl.listJuices(null,null,null)
                                            .get(0);

        juiceDTO.setJuiceName("mezoooo");

        given(juiceService.update(any(UUID.class), any(JuiceDTO.class))).willReturn(Optional.of(juiceDTO));

        mockMvc.perform(patch(JuiceController.JUICE_PATH_ID, juiceDTO.getId()).accept(MediaType.APPLICATION_JSON)
                                                                              .contentType(MediaType.APPLICATION_JSON)
                                                                              .content(objectMapper.writeValueAsString(juiceDTO)))
               .andExpect(status().isNoContent());

        verify(juiceService).update(uuidArgumentCaptor.capture(), juiceArgumentCaptor.capture());

        assertThat(juiceDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(juiceDTO.getJuiceName()).isEqualTo(juiceArgumentCaptor.getValue()
                                                                         .getJuiceName());
    }

    @Test
    void getJuiceByIdNotFound() throws Exception {

        given(juiceService.getJuiceById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(JuiceController.JUICE_PATH_ID, UUID.randomUUID()))
               .andExpect(status().isNotFound());
    }

    @Test
    void testCreateJuiceWithNullJuiceName() throws Exception {

        JuiceDTO juice = JuiceDTO.builder()
                                 .build();

        given(juiceService.addJuice(any())).willReturn(JuiceDTO.builder()
                                                               .id(UUID.randomUUID())
                                                               .build());

        MvcResult mvcResult = mockMvc.perform(post(JuiceController.JUICE_PATH).contentType(MediaType.APPLICATION_JSON)
                                                                              .content(objectMapper.writeValueAsString(juice)))
                                     .andExpect(jsonPath("$.length()", is(2)))
                                     .andExpect(status().isBadRequest())
                                     .andReturn();

        System.out.println(mvcResult.getResponse()
                                    .getContentAsString());

    }
}


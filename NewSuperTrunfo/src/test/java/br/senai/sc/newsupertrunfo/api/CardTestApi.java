package br.senai.sc.newsupertrunfo.api;

import br.senai.sc.newsupertrunfo.controller.CardController;
import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(CardController.class)

public class CardTestApi {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreate() throws Exception {

        Card card = new Card(1, "Poodle", 10, 10.0, 10.0, 10.0);

        String url = "/card/post";

        String requestBody = objectMapper.writeValueAsString(card);

        when(cardService.createCard(any())).thenReturn(card);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAll() throws Exception {

        String url = "/card/get";
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {

        String url = "/card/delete/{cod}";
        Integer resourceId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete(url, resourceId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdate() throws Exception {

        String url = "/card/edit/{cod}";
        Integer cod = 1;
        Card cardUpdated = new Card(1, "poodle", 11, 11.0, 11.0, 11.0);
        String requestBody = objectMapper.writeValueAsString(cardUpdated);

        when(cardService.findCard(cod)).thenReturn(cardUpdated);
        when(cardService.editCard(any())).thenReturn(cardUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put(url, cardUpdated.getCod())
                        .contentType(APPLICATION_JSON)
                        .content(requestBody)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(cardUpdated));
    }

    @Test
    public void testGetById() throws Exception {

        String url = "/card/get/{cod}";
        Long resourceId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(url, resourceId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

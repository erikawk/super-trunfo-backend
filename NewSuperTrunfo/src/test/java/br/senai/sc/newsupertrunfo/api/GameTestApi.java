package br.senai.sc.newsupertrunfo.api;

import br.senai.sc.newsupertrunfo.controller.GameController;
import br.senai.sc.newsupertrunfo.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(GameController.class)
public class GameTestApi {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shuffleCards() throws Exception {

        String url = "/game/shuffle";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void playDices() throws Exception {

        String url = "/game/playDices";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

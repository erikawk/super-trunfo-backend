package br.senai.sc.newsupertrunfo.api;

import br.senai.sc.newsupertrunfo.controller.PlayerController;
import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.model.entity.Player;
import br.senai.sc.newsupertrunfo.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PlayerController.class)
public class PlayerTestApi {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testCreate() throws Exception {

        ArrayList<Card> listCards = new ArrayList<>();
        Player player = new Player(1, 1, 1, 11, "password",
                "email", "name", listCards);

        String url = "/player/post";

        String requestBody = objectMapper.writeValueAsString(player);

        when(playerService.savePlayer(any())).thenReturn(player);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAll() throws Exception {

        String url = "/player/get";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {

        String url = "/player/delete/{id}";
        Long resourceId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete(url, resourceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testUpdate() throws Exception {

        String url = "/player/put/{id}";
        Integer id = 1;
        ArrayList<Card> listCards = new ArrayList<>();
        Player playerUpdated = new Player(1, 2, 1, 11, "password2",
                "email", "name", listCards);

        String requestBody = objectMapper.writeValueAsString(playerUpdated);

        when(playerService.findOnePlayer(id)).thenReturn(Optional.of(playerUpdated));
        when(playerService.savePlayer(any())).thenReturn(playerUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put(url, playerUpdated.getIdPlayer())
                        .contentType(APPLICATION_JSON)
                        .content(requestBody)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(playerUpdated));
    }

    @Test
    public void testGetById() throws Exception {

        String url = "/player/get/{id}";
        Long resourceId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(url, resourceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetByName() throws Exception {

        String url = "/player/get/{name}/{password}";
        String resourceName = "name";
        String resourcePassword = "password";

        mockMvc.perform(MockMvcRequestBuilders.get(url, resourceName, resourcePassword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

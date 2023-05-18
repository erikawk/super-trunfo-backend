package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.model.entity.Pc;
import br.senai.sc.newsupertrunfo.model.entity.Player;
import br.senai.sc.newsupertrunfo.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class GameService {

    private CardService cardService;
    private PlayerService playerService;

    public List<Card> shuffleCards() {
        List<Card> cards = cardService.findAll();
        Collections.shuffle(cards);
        return cards;
    }

    public List<List<Card>> divideCards() {
        List<Card> listShuffled = shuffleCards();
        List<Card> listCardsPC = listShuffled.subList(0, 4);
        List<Card> listCardsPlayer = listShuffled.subList(4, 8);
        return List.of(listCardsPC, listCardsPlayer);
    }

    public Integer playDices() {
        Random random = new Random();
        return random.nextInt((4 - 1) + 1) + 1;
    }

    public List<Integer> featureAssorted() {
        List<Card> listCardsPC = divideCards().get(0);
        List<Card> listCardsPlayer = divideCards().get(1);
        Integer numberAssorted = playDices();

        for (int i = 0; i < listCardsPC.size(); i++) {
            switch (numberAssorted) {
                case 1 -> {
                    return List.of(listCardsPC.get(i).getExpLife(), listCardsPlayer.get(i).getExpLife());
                }
                case 2 -> {
                    return List.of(listCardsPC.get(i).getWeight(), listCardsPlayer.get(i).getWeight());
                }
                case 3 -> {
                    return List.of(listCardsPC.get(i).getHeight(), listCardsPlayer.get(i).getHeight());
                }
                case 4 -> {
                    return List.of(listCardsPC.get(i).getPrice(), listCardsPlayer.get(i).getPrice());
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public String checkWinner() {
        if (featureAssorted().get(0) > featureAssorted().get(1)) {
            return "você perdeu";
        }
        return "você ganhou";
    }

}

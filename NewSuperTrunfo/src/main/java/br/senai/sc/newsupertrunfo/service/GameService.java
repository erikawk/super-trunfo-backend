package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.model.entity.Card;
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

    public List<Card> shuffleCards() {
        List<Card> cards = cardService.findAll();
        Collections.shuffle(cards);
        return cards;
    }

    public List<List<Card>> divideCards() {
        List<Card> listCardsPC = shuffleCards().    subList(0, 2);
        List<Card> listCardsPlayer = shuffleCards().subList(2, 4);

        List<List<Card>> packCards = new ArrayList<>();
        Collections.addAll(packCards, listCardsPC, listCardsPlayer);
        return packCards;
    }

    public Integer playDices() {
        Random random = new Random();
        return random.nextInt(1, 6);
    }

    public Integer featureAssorted(Integer i) {
        List<Card> listCards = divideCards().get(i);
        Integer numberAssorted = playDices();

        for (int j = 0; j < listCards.size(); j++) {
            switch (numberAssorted) {
                case 1 -> {
                    return listCards.get(j).getExpLife();
                }
                case 2 -> {
                    return listCards.get(j).getWeight();
                }
                case 3 -> {
                    return listCards.get(j).getHeight();
                }
                case 4 -> {
                    return listCards.get(j).getPrice();
                }
            }
        }
        return 0;
    }

    public String checkWinner() {
        if(featureAssorted(0) > featureAssorted( 1)){
             return "voce perdeu";
        }
        return "voce ganhou";
    }

}

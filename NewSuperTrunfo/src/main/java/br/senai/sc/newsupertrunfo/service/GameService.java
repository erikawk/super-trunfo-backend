package br.senai.sc.newsupertrunfo.service;
import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.model.entity.Player;
import br.senai.sc.newsupertrunfo.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class GameService {

    private CardService cardService;
    private PlayerService playerService;

    public List<Card> shuffleCards(){
        List<Card> cards = cardService.findAll();
        Collections.shuffle(cards);
        return cards;
    }

//    public List<Card> divideCards(){
//        List<>
//    }

    public Integer playDices(){
        Random random = new Random();
        return random.nextInt(1,6);
    }

//    public Player checkWinner(){
//        if (playerService.findAllPlayers() )
//    }

}

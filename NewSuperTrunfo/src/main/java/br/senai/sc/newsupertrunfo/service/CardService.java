package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }

    public Card findCard (Integer cod){
        return cardRepository.findById(cod).orElseThrow();
    }

    public Card deleteCard(Integer cod){
        Card card = findCard(cod);
        cardRepository.deleteById(cod);
        return card;
    }

    public Card editCard(Card card){
        return cardRepository.save(card);
    }
}

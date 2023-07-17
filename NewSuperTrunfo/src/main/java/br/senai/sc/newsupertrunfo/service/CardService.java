package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;
    private ImageService imageService;

    public Card createCard(Card card, String idImage) {
        card.setImage(imageService.findImg(idImage));
        return cardRepository.save(card);
    }

    public Page<Card> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findAll(pageable);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }

    public Card findCard (Integer cod){
        return cardRepository.findById(cod).orElseThrow();
    }

    public Card findCardPlayer (Integer idPlayer){
        return cardRepository.findById(idPlayer).get();
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

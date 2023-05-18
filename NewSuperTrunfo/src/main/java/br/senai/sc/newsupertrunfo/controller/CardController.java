package br.senai.sc.newsupertrunfo.controller;
import br.senai.sc.newsupertrunfo.model.dto.CardDTO;
import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.service.CardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/card")
@CrossOrigin
public class CardController {
    private CardService cardService;

    @PostMapping("/post")
    public ResponseEntity<Card> createCard(@RequestBody CardDTO cardDTO) {
        Card card = new Card();
        BeanUtils.copyProperties(cardDTO, card);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(card));
    }

    @DeleteMapping("/delete/{cod}")
    public ResponseEntity<Card> deleteCard(@PathVariable Integer cod){
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cardService.deleteCard(cod));
    }

    @PutMapping("/edit/{cod}")
    public ResponseEntity<Card> edit(@PathVariable Integer cod, @RequestBody CardDTO newCard){
        Card card = cardService.findCard(cod);
        BeanUtils.copyProperties(newCard, card);
        return ResponseEntity.status(HttpStatus.OK).body(cardService.editCard(card));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Card>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.findAll());
    }

    @GetMapping("/get/{cod}")
    public ResponseEntity<Card> findCard(@PathVariable Integer cod){
        return  ResponseEntity.status(HttpStatus.OK).body(cardService.findCard(cod));
    }

    @GetMapping("/get/{idPlayer}")
    public ResponseEntity<Card> findCardPlayer(@PathVariable Integer idPlayer){
        return  ResponseEntity.status(HttpStatus.OK).body(cardService.findCardPlayer(idPlayer));
    }

}

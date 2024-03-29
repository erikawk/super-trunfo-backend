package br.senai.sc.newsupertrunfo.controller;
import br.senai.sc.newsupertrunfo.model.dto.CardDTO;
import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.service.CardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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

    @PostMapping("/post/{idImg}")
    public ResponseEntity<Card> createCard(@RequestBody CardDTO cardDTO, @PathVariable String idImg) {
        Card card = new Card();
        BeanUtils.copyProperties(cardDTO, card);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(card, idImg));
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
    public ResponseEntity<Page<Card>> findAll(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.findAll(page, size));
    }

    @GetMapping("/get/{cod}")
    public ResponseEntity<Card> findCard(@PathVariable Integer cod){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.findCard(cod));
    }

    @GetMapping("/get/{idPlayer}")
    public ResponseEntity<Card> findCardPlayer(@PathVariable Integer idPlayer){
        return  ResponseEntity.status(HttpStatus.OK).body(cardService.findCardPlayer(idPlayer));
    }

}

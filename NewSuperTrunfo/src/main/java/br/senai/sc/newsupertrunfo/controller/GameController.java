package br.senai.sc.newsupertrunfo.controller;

import br.senai.sc.newsupertrunfo.model.entity.Card;
import br.senai.sc.newsupertrunfo.service.CardService;
import br.senai.sc.newsupertrunfo.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/game")
@CrossOrigin
public class GameController {
    private GameService gameService;

    @GetMapping("/shuffle")
    public ResponseEntity<List<Card>> shuffleCards(){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.shuffleCards());
    }
    @GetMapping("/playDices")
    public ResponseEntity<Integer> playDices(){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.playDices());
    }

    @GetMapping("/divideCards")
    public ResponseEntity<List<List<Card>>> divideCards(){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.divideCards());
    }

    @GetMapping("/checkWinner")
    public ResponseEntity<String> checkWinner(){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.checkWinner());
    }
}

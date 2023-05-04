package br.senai.sc.newsupertrunfo.controller;

import br.senai.sc.newsupertrunfo.model.dto.PlayerDTO;
import br.senai.sc.newsupertrunfo.model.entity.Player;
import br.senai.sc.newsupertrunfo.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/player")
@AllArgsConstructor
@CrossOrigin
public class PlayerController {

    private PlayerService playerService;

    // criar
    @PostMapping("/post")
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO playerDTO){
        Player player = new Player();
        BeanUtils.copyProperties(playerDTO, player);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.savePlayer(player));
    }

    // editar
    @PutMapping("/put/{id}")
    public ResponseEntity<Player> editPlayer(@RequestBody PlayerDTO playerDTO, @PathVariable Integer id){
        Player player = playerService.findOnePlayer(id).get();
        BeanUtils.copyProperties(playerDTO, player);
        return ResponseEntity.status(HttpStatus.OK).body(playerService.savePlayer(player));
    }

    // deletar
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(playerService.deletePlayer(id));
    }

    // procura todos
    @GetMapping("/get")
    public ResponseEntity<List<Player>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.findAllPlayers());
    }

    // procura um player
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Player>> findOnePlayer(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.findOnePlayer(id));
    }

    // procura por nome
    @GetMapping("/get/{name}/{password}")
    public ResponseEntity<Player> findByName(@PathVariable String name, @PathVariable String password){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.findByName(name, password));
    }

}

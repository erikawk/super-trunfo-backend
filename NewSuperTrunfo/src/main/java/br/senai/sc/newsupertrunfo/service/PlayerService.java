package br.senai.sc.newsupertrunfo.service;

import br.senai.sc.newsupertrunfo.model.entity.Player;
import br.senai.sc.newsupertrunfo.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService{

    private PlayerRepository playerRepository;

    // criar e editar
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    // deletar
    public Player deletePlayer(Integer id){
        Player player = findOnePlayer(id).get();
        playerRepository.deleteById(id);
        return player;
    }

    // procurar todos
    public List<Player> findAllPlayers(){
        return playerRepository.findAll();
    }

    // procura um
    public Optional<Player> findOnePlayer(Integer id){
        return playerRepository.findById(id);
    }

    // procura por nome
    public Player findByName (String name, String password){
        Player player = playerRepository.findByName(name, password);
        if(player == null){
            throw new RuntimeException();
        }
        return player;
    }
}

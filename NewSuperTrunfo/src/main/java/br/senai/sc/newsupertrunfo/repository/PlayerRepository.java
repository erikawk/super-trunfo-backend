package br.senai.sc.newsupertrunfo.repository;

import br.senai.sc.newsupertrunfo.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    @Query("SELECT u FROM Player u WHERE u.name = :name AND u.password= :password")
    Player findByName(String name, String password);
}

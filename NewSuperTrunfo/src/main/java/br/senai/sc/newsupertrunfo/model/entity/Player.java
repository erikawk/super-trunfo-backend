package br.senai.sc.newsupertrunfo.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlayer;
    private Integer wonsGames, lossesGames, age;
    private String password, email;
    @Column(unique = true)
    private String name;
    @OneToMany
    @JoinColumn(name = "id_player")
    private List<Card> cards;
}

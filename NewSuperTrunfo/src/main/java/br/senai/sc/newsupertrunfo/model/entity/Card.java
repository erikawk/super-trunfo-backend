package br.senai.sc.newsupertrunfo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_card")
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer cod;
    private String race;
    private Integer expLife;
    private Integer weight, height, price;

    @OneToOne
    private Image image;
}

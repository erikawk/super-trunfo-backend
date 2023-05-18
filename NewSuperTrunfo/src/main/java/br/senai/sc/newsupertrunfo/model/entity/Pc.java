package br.senai.sc.newsupertrunfo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPc;

    @OneToMany
    @JoinColumn(name = "id_pc")
    private List<Card> listCardsPc;
}

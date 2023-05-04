package br.senai.sc.newsupertrunfo.model.dto;
import br.senai.sc.newsupertrunfo.model.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private Integer wonsGames, lossesGames, age;
    private String name, password, email;
    private List<Card> cards;
}

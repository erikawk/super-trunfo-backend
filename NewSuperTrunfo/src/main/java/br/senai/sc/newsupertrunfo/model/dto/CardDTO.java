package br.senai.sc.newsupertrunfo.model.dto;

import br.senai.sc.newsupertrunfo.model.entity.Player;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    @NotNull
//    private String race;
    @NotNull
    private Integer expLife;
    @NotNull
    private Integer weight, height, price;

}

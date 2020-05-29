package br.com.tiagocalixto.pokedex.external_api.national_dex.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PokemonStatsNationalDexDto implements Serializable {

    private Long hp;
    private Long attack;
    private Long defense;
    private Long specialAttack;
    private Long specialDefense;
    private Long speed;
}

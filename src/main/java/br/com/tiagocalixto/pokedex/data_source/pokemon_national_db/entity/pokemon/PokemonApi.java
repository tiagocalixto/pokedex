package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.AbilityApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.MoveApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.TypeApi;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PokemonApi implements Serializable {

    private Long number;
    private String name;
    private Long weight;
    private BigDecimal height;
    private PokemonStatsApi stats;
    private List<TypeApi> type;
    private PokemonSpecieApi specie;
    private List<MoveApi> move;
    private List<AbilityApi> ability;
}

package br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.entity.evolution_chain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EvolutionChainPokemonApi {

    String name;
    Long number;
    List<EvolutionChainEvolveToApi> evolveTo;
}

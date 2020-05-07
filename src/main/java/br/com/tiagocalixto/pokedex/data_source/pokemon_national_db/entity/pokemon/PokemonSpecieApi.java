package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.evolution_chain.EvolutionChainPokemonApi;
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
public class PokemonSpecieApi implements Serializable {

    String generation;
    Long idEvolutionChain;
    PokemonSpecieEvolvedFrom evolvedFrom;
    EvolutionChainPokemonApi evolveTo;
}

package br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.pokemon;

import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.evolution_chain.EvolutionChainEvolvedFromNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.evolution_chain.EvolutionChainNationalDb;
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
public class PokemonSpecieNationalDb implements Serializable {

    String generation;
    Long idEvolutionChain;
    EvolutionChainEvolvedFromNationalDb evolvedFrom;
    EvolutionChainNationalDb evolveTo;
}

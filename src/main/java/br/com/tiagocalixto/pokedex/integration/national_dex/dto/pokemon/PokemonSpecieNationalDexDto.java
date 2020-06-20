package br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon;

import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolvedFromNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolutionChainNationalDexDto;
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
public class PokemonSpecieNationalDexDto implements Serializable {

    String generation;
    Long idEvolutionChain;
    EvolvedFromNationalDexDto evolvedFrom;
    EvolutionChainNationalDexDto evolveTo;
}

package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.evolution_chain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EvolutionChainNationalDb implements Serializable {

    private String name;
    private Long number;
    private Long idChain;
    private List<EvolutionChainEvolveToNationalDb> evolveTo;
}

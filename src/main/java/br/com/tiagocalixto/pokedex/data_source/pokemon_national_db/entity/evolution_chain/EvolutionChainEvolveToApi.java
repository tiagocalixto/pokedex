package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.evolution_chain;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.TypeApi;
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
public class EvolutionChainEvolveToApi implements Serializable {

    private String name;
    private Long number;
    private List<TypeApi> type;
}

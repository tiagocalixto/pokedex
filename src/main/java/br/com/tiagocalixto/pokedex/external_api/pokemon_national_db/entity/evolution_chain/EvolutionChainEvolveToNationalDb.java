package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.evolution_chain;

import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.TypeNationalDb;
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
public class EvolutionChainEvolveToNationalDb implements Serializable {

    private String name;
    private Long number;
    private List<TypeNationalDb> type;
}

package br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain;


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
public class EvolvedFromNationalDexDto implements Serializable {

    Long id;
    String name;
}

package br.com.tiagocalixto.pokedex.external_api.national_dex.dto.evolution_chain;

import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.TypeNationalDexDto;
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
public class EvolveToNationalDexDto implements Serializable {

    private String name;
    private Long number;
    private List<TypeNationalDexDto> type;
}

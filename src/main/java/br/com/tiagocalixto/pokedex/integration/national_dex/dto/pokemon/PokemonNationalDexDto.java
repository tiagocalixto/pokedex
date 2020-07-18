package br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon;

import br.com.tiagocalixto.pokedex.integration.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
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
public class PokemonNationalDexDto implements Serializable {

    private Long id;
    private Long number;
    private String name;
    private Long weight;
    private BigDecimal height;
    private String urlPicture;
    private PokemonStatsNationalDexDto stats;
    private List<TypeNationalDexDto> type;
    private PokemonSpecieNationalDexDto specie;
    private List<MoveNationalDexDto> move;
    private List<AbilityNationalDexDto> ability;
}

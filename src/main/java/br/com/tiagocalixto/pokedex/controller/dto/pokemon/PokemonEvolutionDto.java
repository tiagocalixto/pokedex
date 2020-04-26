package br.com.tiagocalixto.pokedex.controller.dto.pokemon;

import br.com.tiagocalixto.pokedex.controller.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.input_rules.groups.SecondStepValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Evolution", subTypes = {Object.class})
public class PokemonEvolutionDto implements Serializable {

    @NotNull(message = POKEMON_IS_REQUIRED, groups = FirstStepValidation.class)
    @Valid
    @ApiModelProperty(notes = "Pokemon evolution info", dataType = "object", position = 1)
    private PokemonAbbreviatedDto pokemon;

    @NotNull(message = LEVEL_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_LEVEL_RANGE, groups = SecondStepValidation.class)
    @Max(value = 100, message = INVALID_LEVEL_RANGE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Evolution level", dataType = "integer", example = "16", position = 2)
    private Long level;
}

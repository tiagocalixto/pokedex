package br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon;

import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionStoneDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionTriggerDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.EvolutionRule;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.SecondStepValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Evolution", subTypes = {Object.class})
@EvolutionRule(groups = SecondStepValidation.class)
public class PokemonEvolutionDto implements Serializable {

    @NotNull(message = POKEMON_IS_REQUIRED, groups = FirstStepValidation.class)
    @Valid
    @ApiModelProperty(notes = "Pokemon evolution info", dataType = "object", position = 1)
    private PokemonAbbreviatedDto pokemon;

    @NotNull(message = EVOLUTION_TRIGGER_REQUIRED, groups = FirstStepValidation.class)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    @ApiModelProperty(notes = "Evolution trigger", dataType = "Enum", example = "LEVEL_UP, TRADE, USE_ITEM", position = 2)
    private EvolutionTriggerDtoEnum trigger;

    @ApiModelProperty(notes = "Evolution level", dataType = "integer", example = "16", position = 3)
    private Long level;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    @ApiModelProperty(notes = "Evolution item", dataType = "Enum",
            example = "FIRE_STONE, WATER_STONE, THUNDER_STONE, LEAF_STONE, MOON_STONE", position = 4)
    private EvolutionStoneDtoEnum item;
}

package br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon;

import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.SecondStepValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Stats", subTypes = {Object.class})
public class PokemonStatsDto implements Serializable {

    @NotNull(message = HP_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Hp", dataType = "integer", example = "44", position = 1)
    private Long hp;

    @NotNull(message = ATTACK_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Attack", dataType = "integer", example = "48", position = 2)
    private Long attack;

    @NotNull(message = DEFENSE_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Defense", dataType = "integer", example = "65", position = 3)
    private Long defense;

    @NotNull(message = SPECIAL_ATTACK_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Special Attack", dataType = "integer", example = "50", position = 4)
    private Long specialAttack;

    @NotNull(message = SPECIAL_DEFENSE_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Special Defense", dataType = "integer", example = "64", position = 5)
    private Long specialDefense;

    @NotNull(message = SPEED_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Speed", dataType = "integer", example = "43", position = 6)
    private Long speed;
}

package br.com.tiagocalixto.pokedex.controller.v1.dto;

import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.TypeDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.FirstStepValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.DESCRIPTION_FIELD_IS_REQUIRED;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Type", subTypes = {Object.class})
public class TypeDto implements Serializable {


    @NotNull(message = DESCRIPTION_FIELD_IS_REQUIRED, groups = FirstStepValidation.class)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    @ApiModelProperty(notes = "Type description", dataType = "string",
            example = "GRASS, POISON, FIRE, FLYING, WATER, BUG, NORMAL, ELECTRIC, GROUND, FAIRY, " +
                    "FIGHTING, PSYCHIC, ROCK, STEEL, ICE, GHOST, DRAGON ", position = 1)
    private TypeDtoEnum description;

}

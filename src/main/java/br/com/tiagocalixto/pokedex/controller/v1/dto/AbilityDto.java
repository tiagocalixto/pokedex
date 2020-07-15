package br.com.tiagocalixto.pokedex.controller.v1.dto;


import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.SecondStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.ThirdStepValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Ability", subTypes = {Object.class})
public class AbilityDto implements Serializable {


    @NotBlank(message = DESCRIPTION_FIELD_IS_REQUIRED, groups = FirstStepValidation.class)
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú' ]*$", message = DESCRIPTION_FIELD_IS_INVALID, groups = SecondStepValidation.class)
    @Size(min = 3, max = 50, message = DESCRIPTION_LENGTH_INVALID, groups = ThirdStepValidation.class)
    @ApiModelProperty(notes = "Ability description", dataType = "string", example = "Rain Dish", position = 2)
    private String description;
}

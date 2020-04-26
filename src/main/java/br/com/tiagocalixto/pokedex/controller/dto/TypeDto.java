package br.com.tiagocalixto.pokedex.controller.dto;

import br.com.tiagocalixto.pokedex.controller.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.input_rules.groups.SecondStepValidation;
import br.com.tiagocalixto.pokedex.controller.input_rules.groups.ThirdStepValidation;
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

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Type", subTypes = {Object.class})
public class TypeDto implements Serializable {


    @NotBlank(message = DESCRIPTION_FIELD_IS_REQUIRED, groups = FirstStepValidation.class)
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú ']*$", message = DESCRIPTION_FIELD_IS_INVALID, groups = SecondStepValidation.class)
    @Size(min = 3, max = 50, message = DESCRIPTION_LENGTH_INVALID, groups = ThirdStepValidation.class)
    @ApiModelProperty(notes = "Type description", dataType = "string", example = "Water", position = 1)
    private String description;

}

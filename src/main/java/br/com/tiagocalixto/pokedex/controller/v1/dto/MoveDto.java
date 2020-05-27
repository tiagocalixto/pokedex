package br.com.tiagocalixto.pokedex.controller.v1.dto;


import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.SecondStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.ThirdStepValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Move", subTypes = {Object.class})
public class MoveDto implements Serializable {

    @NotBlank(message = DESCRIPTION_FIELD_IS_REQUIRED, groups = FirstStepValidation.class)
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú' ]*$", message = DESCRIPTION_FIELD_IS_INVALID, groups = SecondStepValidation.class)
    @Size(min = 3, max = 50, message = DESCRIPTION_LENGTH_INVALID, groups = ThirdStepValidation.class)
    @ApiModelProperty(notes = "Move description", dataType = "string", example = "Water Gun", position = 1)
    private String description;

    @NotNull(message = TYPE_IS_REQUIRED, groups = FirstStepValidation.class)
    @Valid
    @ApiModelProperty(notes = "Move Type", dataType = "object", position = 2)
    private TypeDto type;

    @NotNull(message = PP_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "PP", dataType = "integer", example = "25", position = 3)
    private Long pp;

    @NotNull(message = POWER_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @Max(value = 1000, message = INVALID_RANGE_1_1000, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Power", dataType = "integer", example = "40", position = 4)
    private Long power;

    @NotNull(message = ACCURACY_REQUIRED, groups = FirstStepValidation.class)
    @DecimalMin(value = "0.1", message = ACCURACY_INVALID_RANGE, groups = SecondStepValidation.class)
    @DecimalMax(value = "100.00", message = ACCURACY_INVALID_RANGE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Accuracy", dataType = "number", example = "100", position = 5)
    private BigDecimal accuracy;

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú0-9 .,']*$", message = ABOUT_ESPECIAL_CHAR, groups = SecondStepValidation.class)
    @Size(min = 3, max = 255, message = ABOUT_LENGTH_INVALID, groups = ThirdStepValidation.class)
    @ApiModelProperty(notes = "About", dataType = "string",
            example = "Water Gun deals damage with no additional effect.", position = 6)
    private String about = "";
}

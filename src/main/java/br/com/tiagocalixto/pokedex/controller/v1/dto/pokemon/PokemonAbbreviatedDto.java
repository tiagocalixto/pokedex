package br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon;

import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.PokemonAbbreviatedRule;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.FirstStepValidation;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.SecondStepValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Pokemon Evolution", subTypes = {Object.class})
@PokemonAbbreviatedRule(groups = FirstStepValidation.class)
public class PokemonAbbreviatedDto implements Serializable {


    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Min(value = 1, message = NUMBER_INVALID_RANGE, groups = SecondStepValidation.class)
    @Max(value = 151, message = NUMBER_INVALID_RANGE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "PokemonDto number", dataType = "string", example = "8", position = 1)
    private Long number = 0L;

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú .']*$", message = NAME_IS_INVALID, groups = SecondStepValidation.class)
    @Length(min = 3, max = 50, message = NAME_INVALID_SIZE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "PokemonDto name", dataType = "string", example = "Wartortle", position = 2)
    private String name = "";

    @ApiModelProperty(notes = "Url with pokemon picture", dataType = "string", position = 3, hidden = true)
    private String urlPicture;

    @ApiModelProperty(notes = "Pokemon type", dataType = "array", position = 4, hidden = true)
    private List<TypeDto> type;
}

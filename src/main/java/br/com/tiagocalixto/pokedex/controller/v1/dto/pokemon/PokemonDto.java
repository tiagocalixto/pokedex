package br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon;


import br.com.tiagocalixto.pokedex.controller.v1.dto.AbilityDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.HasDuplicatedItemInList;
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
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Pokemon", subTypes = {Object.class})
public class PokemonDto implements Serializable {

    @NotNull(message = NUMBER_IS_REQUIRED)
    @Min(value = 1, message = NUMBER_INVALID_RANGE, groups = SecondStepValidation.class)
    @Max(value = 151, message = NUMBER_INVALID_RANGE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "PokemonDto number", dataType = "integer", example = "7", position = 1)
    private Long number;

    @NotBlank(message = NAME_IS_REQUIRED, groups = FirstStepValidation.class)
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú .']*$", message = NAME_IS_INVALID, groups = SecondStepValidation.class)
    @Length(min = 3, max = 50, message = NAME_INVALID_SIZE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Pokemon name", dataType = "string", example = "Squirtle", position = 2)
    private String name;

    @NotNull(message = WEIGHT_IS_REQUIRED, groups = FirstStepValidation.class)
    @Min(value = 1, message = WEIGHT_INVALID_RANGE, groups = SecondStepValidation.class)
    @Max(value = 1000, message = WEIGHT_INVALID_RANGE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Pokemon weight", dataType = "integer", example = "9", position = 3)
    private Long weight;

    @NotNull(message = HEIGHT_IS_REQUIRED, groups = FirstStepValidation.class)
    @DecimalMin(value = "0.1", message = HEIGHT_INVALID_RANGE, groups = SecondStepValidation.class)
    @DecimalMax(value = "25.00", message = HEIGHT_INVALID_RANGE, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Pokemon height", dataType = "number", example = "0.5", position = 4)
    private BigDecimal height;

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú0-9 .,']*$", message = ABOUT_ESPECIAL_CHAR, groups = SecondStepValidation.class)
    @Size(min = 3, max = 255, message = ABOUT_LENGTH_INVALID, groups = ThirdStepValidation.class)
    @ApiModelProperty(notes = "About Pokemon", dataType = "string",
            example = "Squirtle is a Water type Pokemon. It is known as the Tiny Turtle Pokémon.", position = 5)
    private String about = "";

    @NotBlank(message = URL_PICTURE_IS_REQUIRED, groups = FirstStepValidation.class)
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",
            message = URL_PICTURE_INVALID_FORMAT, groups = SecondStepValidation.class)
    @ApiModelProperty(notes = "Url with pokemon picture", dataType = "string",
            example = "https://img.pokemondb.net/artwork/squirtle.jpg", position = 6)
    private String urlPicture;

    @NotNull(message = STATS_IS_REQUIRED, groups = FirstStepValidation.class)
    @Valid
    @ApiModelProperty(notes = "Stats", dataType = "object", position = 7)
    private PokemonStatsDto stats;

    @HasDuplicatedItemInList(message = DUPLICATED_ITEM_TYPE)
    @NotEmpty(message = TYPE_IS_REQUIRED, groups = FirstStepValidation.class)
    @Valid
    @ApiModelProperty(notes = "Type", dataType = "array", position = 8)
    private List<TypeDto> type;

    @Valid
    @ApiModelProperty(notes = "Pokemon", dataType = "object", position = 9)
    private List<PokemonEvolutionDto> evolveTo;

    @Valid
    @ApiModelProperty(notes = "Pokemon", dataType = "object", position = 10)
    private PokemonEvolutionDto evolvedFrom;

    @HasDuplicatedItemInList(message = DUPLICATED_ITEM_MOVE)
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Valid
    @ApiModelProperty(notes = "Pokemon Moves", dataType = "array", position = 11)
    private List<PokemonMoveDto> move = Collections.emptyList();

    @HasDuplicatedItemInList(message = DUPLICATED_ITEM_ABILITY)
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Valid
    @ApiModelProperty(notes = "Ability", dataType = "array", position = 12)
    private List<AbilityDto> ability = Collections.emptyList();

    @HasDuplicatedItemInList(message = DUPLICATED_ITEM_WEAKNESS)
    @Valid
    @ApiModelProperty(notes = "weakness", dataType = "array", position = 13)
    private List<TypeDto> weakness;
}

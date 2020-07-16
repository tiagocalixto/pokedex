package br.com.tiagocalixto.pokedex.mock;

import br.com.tiagocalixto.pokedex.controller.v1.dto.AbilityDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.MoveDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionTriggerDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.TypeDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.*;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.math.BigDecimal;
import java.util.List;

public class MocksDto {

    public static PokemonDto createPokemon() {

        return PokemonDto.builder()
                .id(new RandomDataGenerator().nextLong(1L, 1000L))
                .number(64L)
                .name("Kadabra")
                .weight(56L)
                .height(BigDecimal.valueOf(1.3))
                .about("It stares at a silver spoon to amplify its psychic powers before it lets loose. " +
                        "Apparently, gold spoons are no good.")
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/064.png")
                .stats(createPokemonStats())
                .type(List.of(createType()))
                .evolvedFrom(createPokemonEvolutionLevelUp(createEvolvedFrom(), 16L))
                .evolveTo(List.of(createPokemonEvolutionTrade(createEvolveTo())))
                .move(List.of(createPokemonMove(createMove())))
                .ability(List.of(createAbility()))
                .weakness(List.of(createWeakness()))
                .build();
    }

    public static PokemonStatsDto createPokemonStats(){

        return PokemonStatsDto.builder()
                .attack(35L)
                .defense(30L)
                .hp(40L)
                .speed(105L)
                .specialAttack(120L)
                .specialDefense(70L)
                .build();
    }

    public static TypeDto createType(){

        return TypeDto.builder()
                .description(TypeDtoEnum.PSYCHIC)
                .build();
    }

    public static PokemonEvolutionDto createPokemonEvolutionLevelUp(PokemonAbbreviatedDto pokemon, Long level){

        return PokemonEvolutionDto.builder()
                .pokemon(pokemon)
                .item(null)
                .trigger(EvolutionTriggerDtoEnum.LEVEL_UP)
                .level(level)
                .build();
    }

    public static PokemonEvolutionDto createPokemonEvolutionTrade(PokemonAbbreviatedDto pokemon){

        return PokemonEvolutionDto.builder()
                .pokemon(pokemon)
                .item(null)
                .level(null)
                .trigger(EvolutionTriggerDtoEnum.TRADE)
                .build();
    }

    public static PokemonAbbreviatedDto createEvolvedFrom(){

        return PokemonAbbreviatedDto.builder()
                .name("Abra")
                .number(63L)
                .type(List.of(createType()))
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/063.png")
                .build();
    }

    public static PokemonAbbreviatedDto createEvolveTo(){

        return PokemonAbbreviatedDto.builder()
                .name("Alakazam")
                .number(65L)
                .type(List.of(createType()))
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/065.png")
                .build();
    }

    public static MoveDto createMove(){

        return MoveDto.builder()
                .about("Inflicts regular damage. Has a 10% chance to confuse the target.")
                .description("Confusion")
                .type(createType())
                .accuracy(BigDecimal.valueOf(100))
                .power(50L)
                .pp(25L)
                .build();
    }

    public static PokemonMoveDto createPokemonMove(MoveDto move){

        return PokemonMoveDto.builder()
                .move(move)
                .levelLearn(1L)
                .build();
    }

    public static AbilityDto createAbility(){

        return AbilityDto.builder()
                .description("Magic-guard")
                .build();
    }

    public static TypeDto createWeakness(){

        return TypeDto.builder()
                .description(TypeDtoEnum.GHOST)
                .build();
    }
}

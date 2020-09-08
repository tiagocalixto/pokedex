package br.com.tiagocalixto.pokedex.mock;

import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolutionChainNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolveToNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolvedFromNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonSpecieNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonStatsNationalDexDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class MocksNationalDexDto {

    public static PokemonNationalDexDto createPokemon() {

        return PokemonNationalDexDto.builder()
                .id(new Random().nextLong())
                .number(64L)
                .name("Kadabra")
                .weight(56L)
                .height(BigDecimal.valueOf(1.3))
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/064.png")
                .stats(createPokemonStats())
                .type(List.of(createType()))
                .move(List.of(createMove()))
                .ability(List.of(createAbility()))
                .specie(createSpecie())
                .build();
    }

    public static PokemonStatsNationalDexDto createPokemonStats() {

        return PokemonStatsNationalDexDto.builder()
                .attack(35L)
                .defense(30L)
                .hp(40L)
                .speed(105L)
                .specialAttack(120L)
                .specialDefense(70L)
                .build();
    }

    public static TypeNationalDexDto createType() {

        return TypeNationalDexDto.builder()
                .description(TypeEnum.PSYCHIC.toString())
                .build();
    }

    public static MoveNationalDexDto createMove() {

        return MoveNationalDexDto.builder()
                .about("Inflicts regular damage. Has a 10% chance to confuse the target.")
                .description("Confusion")
                .type(createType())
                .accuracy(BigDecimal.valueOf(100))
                .power(50L)
                .pp(25L)
                .build();
    }

    public static AbilityNationalDexDto createAbility() {

        return AbilityNationalDexDto.builder()
                .description("Magic-guard")
                .build();
    }

    public static EvolvedFromNationalDexDto createEvolvedFrom() {

        return EvolvedFromNationalDexDto.builder()
                .id(63L)
                .name("Abra")
                .build();
    }

    public static EvolveToNationalDexDto createEvolveTo() {

        return EvolveToNationalDexDto.builder()
                .name("Alakazam")
                .number(65L)
                .type(List.of(createType()))
                .build();
    }

    public static EvolutionChainNationalDexDto createEvolutionChain() {

        return EvolutionChainNationalDexDto.builder()
                .evolveTo(List.of(createEvolveTo()))
                .idChain(new Random().nextLong())
                .name("Kadabra")
                .number(64L)
                .build();
    }

    public static PokemonSpecieNationalDexDto createSpecie() {

        return PokemonSpecieNationalDexDto.builder()
                .evolvedFrom(createEvolvedFrom())
                .evolveTo(createEvolutionChain())
                .generation("First")
                .idEvolutionChain(new Random().nextLong())
                .build();
    }
}

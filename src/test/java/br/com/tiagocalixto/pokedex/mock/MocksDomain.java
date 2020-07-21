package br.com.tiagocalixto.pokedex.mock;

import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonStats;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class MocksDomain {

    public static Pokemon createPokemon() {

        return createPokemon(new RandomDataGenerator().nextLong(1L, 1000L));
    }

    public static Pokemon createPokemon(Long id) {

        return Pokemon.builder()
                .id(id)
                .number(64L)
                .name("Kadabra")
                .weight(56L)
                .height(BigDecimal.valueOf(1.3))
                .about("It stares at a silver spoon to amplify its psychic powers before it lets loose. " +
                        "Apparently, gold spoons are no good.")
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/064.png")
                .stats(createPokemonStats())
                .type(List.of(createType()))
                .evolvedFrom(createEvolvedFrom(createPokemonEvolvedFrom()))
                .evolveTo(List.of(createEvolveTo(createPokemonEvolveTo())))
                .move(List.of(createPokemonMove(createMove())))
                .ability(List.of(createAbility()))
                .weakness(List.of(createWeakness()))
                .build();
    }

    public static PokemonStats createPokemonStats() {

        return PokemonStats.builder()
                .attack(35L)
                .defense(30L)
                .hp(40L)
                .speed(105L)
                .specialAttack(120L)
                .specialDefense(70L)
                .build();
    }

    public static Type createType() {

        return Type.builder()
                .description(TypeEnum.PSYCHIC)
                .build();
    }

    public static PokemonEvolution createEvolvedFrom(Pokemon pokemon) {

        return PokemonEvolution.builder()
                .pokemon(pokemon)
                .trigger(EvolutionTriggerEnum.LEVEL_UP)
                .level(16L)
                .build();
    }

    public static PokemonEvolution createEvolveTo(Pokemon pokemon) {

        return PokemonEvolution.builder()
                .pokemon(pokemon)
                .trigger(EvolutionTriggerEnum.TRADE)
                .build();
    }

    public static Pokemon createPokemonEvolvedFrom() {

        return Pokemon.builder()
                .id(new RandomDataGenerator().nextLong(1, 1000))
                .number(63L)
                .name("Abra")
                .weight(56L)
                .height(BigDecimal.valueOf(1.3))
                .about("Abra is the first evolution of chain")
                .stats(createPokemonStats())
                .type(List.of(createType()))
                .evolvedFrom(null)
                .evolveTo(Collections.emptyList())
                .move(Collections.emptyList())
                .ability(Collections.emptyList())
                .weakness(Collections.emptyList())
                .build();
    }

    public static Pokemon createPokemonEvolveTo() {

        return Pokemon.builder()
                .id(new RandomDataGenerator().nextLong(1, 1000))
                .number(65L)
                .name("Alakazam")
                .weight(56L)
                .height(BigDecimal.valueOf(1.3))
                .about("The last evolution of this guys")
                .stats(createPokemonStats())
                .type(List.of(createType()))
                .evolvedFrom(null)
                .evolveTo(Collections.emptyList())
                .move(Collections.emptyList())
                .ability(Collections.emptyList())
                .weakness(Collections.emptyList())
                .build();
    }

    public static PokemonMove createPokemonMove(Move move) {

        return PokemonMove.builder()
                .move(move)
                .levelLearn(1L)
                .build();
    }

    public static Move createMove() {

        return Move.builder()
                .about("Inflicts regular damage. Has a 10% chance to confuse the target.")
                .description("Confusion")
                .type(createType())
                .accuracy(BigDecimal.valueOf(100))
                .power(50L)
                .pp(25L)
                .build();
    }

    public static Ability createAbility() {

        return Ability.builder()
                .description("Magic-guard")
                .build();
    }

    public static Type createWeakness() {

        return Type.builder()
                .description(TypeEnum.PSYCHIC)
                .build();
    }
}

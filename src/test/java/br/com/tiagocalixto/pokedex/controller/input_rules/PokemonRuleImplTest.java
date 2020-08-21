package br.com.tiagocalixto.pokedex.controller.input_rules;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonAbbreviatedDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonEvolutionDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonMoveDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.PokemonRuleImpl;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintValidatorContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class PokemonRuleImplTest {

    @InjectMocks
    private PokemonRuleImpl rule;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(ConstraintValidatorContext.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenIsValid_thenReturnTrue() {

        PokemonDto pokemon = MocksDto.createPokemon();

        boolean result = rule.isValid(pokemon, context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonWithWeaknessEqualsType_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeakness(pokemon.getType());

        boolean result = rule.isValid(pokemon, context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonWithWeaknessNull_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeakness(null);

        boolean result = rule.isValid(pokemon, context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonWithEvolveToDuplicated_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        PokemonEvolutionDto evolution = pokemon.getEvolveTo().get(0);
        pokemon.setEvolveTo(new ArrayList<>());
        pokemon.getEvolveTo().add(evolution);
        pokemon.getEvolveTo().add(evolution);

        boolean result = rule.isValid(pokemon, context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolveToNull_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setEvolveTo(null);

        boolean result = rule.isValid(pokemon, context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonMoveDuplicated_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        PokemonMoveDto move = pokemon.getMove().get(0);
        pokemon.setMove(new ArrayList<>());
        pokemon.getMove().add(move);
        pokemon.getMove().add(move);

        boolean result = rule.isValid(pokemon, context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonMoveNull_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setMove(null);

        boolean result = rule.isValid(pokemon, context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolveToIsSameNameAndNumberAsPokemon_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName(pokemon.getName());
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(pokemon.getNumber());

        boolean result = rule.isValid(pokemon, context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolvedFromIsSameNameAndNumberAsPokemon_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(pokemon.getName());
        pokemon.getEvolvedFrom().getPokemon().setNumber(pokemon.getNumber());

        boolean result = rule.isValid(pokemon, context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolvedFromNull_whenIsValid_thenReturnFalse() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setEvolvedFrom(null);

        boolean result = rule.isValid(pokemon, context);

        assertTrue(result);
    }
}

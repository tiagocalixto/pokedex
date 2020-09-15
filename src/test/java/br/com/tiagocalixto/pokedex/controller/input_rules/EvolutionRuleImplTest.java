package br.com.tiagocalixto.pokedex.controller.input_rules;

import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionStoneDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionTriggerDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.EvolutionRuleImpl;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class EvolutionRuleImplTest {

    @InjectMocks
    private EvolutionRuleImpl rule;

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
    void givenPokemonEvolutionLevelUpValid_whenIsValid_thenReturnTrue() {

        PokemonDto pokemonDto = MocksDto.createPokemon();

        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolutionUseItemValid_whenIsValid_thenReturnTrue() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        pokemonDto.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.USE_ITEM);
        pokemonDto.getEvolvedFrom().setLevel(null);
        pokemonDto.getEvolvedFrom().setItem(EvolutionStoneDtoEnum.FIRE_STONE);

        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolutionTrade_whenIsValid_thenReturnTrue() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        pokemonDto.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.TRADE);
        pokemonDto.getEvolvedFrom().setLevel(null);
        pokemonDto.getEvolvedFrom().setItem(null);

        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolutionLevelNull_whenIsValid_thenReturnFalse() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        pokemonDto.getEvolvedFrom().setLevel(null);

        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolutionLevelInvalidRange_whenIsValid_thenReturnFalse() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        pokemonDto.getEvolvedFrom().setLevel(1255L);

        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolutionUseItemInvalid_whenIsValid_thenReturnFalse() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        pokemonDto.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.USE_ITEM);
        pokemonDto.getEvolvedFrom().setLevel(null);
        pokemonDto.getEvolvedFrom().setItem(null);


        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertFalse(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonEvolutionEvolutionTriggerNull_whenIsValid_thenReturnFalse() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        pokemonDto.getEvolvedFrom().setTrigger(null);

        boolean result = rule.isValid(pokemonDto.getEvolvedFrom(), context);

        assertFalse(result);
    }

}

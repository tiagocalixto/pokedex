package br.com.tiagocalixto.pokedex.controller.input_rules;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonAbbreviatedDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.PokemonAbbreviatedRuleImpl;
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
class PokemonAbbreviatedRuleImplTest {

    @InjectMocks
    private PokemonAbbreviatedRuleImpl rule;

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
    void givenPokemonAbbreviated_whenIsValid_thenReturnTrue() {

        PokemonAbbreviatedDto pokemon = MocksDto.createEvolvedFrom();

        boolean result = rule.isValid(pokemon, context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenPokemonAbbreviatedWithNoNameAndNoNumber_whenIsValid_thenReturnFalse() {

        PokemonAbbreviatedDto pokemon = MocksDto.createEvolvedFrom();
        pokemon.setNumber(null);
        pokemon.setName(null);

        boolean result = rule.isValid(pokemon, context);

        assertFalse(result);
    }
}

package br.com.tiagocalixto.pokedex.controller.input_rules;

import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.HasDuplicatedItemInListImpl;
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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class HasADuplicatedItemInListImplTest {

    @InjectMocks
    private HasDuplicatedItemInListImpl rule;

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
    void givenEmptyList_whenIsValid_thenReturnTrue() {


        boolean result = rule.isValid(new ArrayList<>(), context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenListWithDifferentItems_whenIsValid_thenReturnTrue() {


        boolean result = rule.isValid(Arrays.asList("first", "second"), context);

        assertTrue(result);
    }

    @Test
    @SneakyThrows
    void givenListWithSameItems_whenIsValid_thenReturnTrue() {


        boolean result = rule.isValid(Arrays.asList("first", "first"), context);

        assertFalse(result);
    }
}

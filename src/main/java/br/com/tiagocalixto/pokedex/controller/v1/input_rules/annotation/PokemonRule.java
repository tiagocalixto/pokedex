package br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation;


import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.PokemonRuleImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.INVALID_POKEMON_GENERIC;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {PokemonRuleImpl.class})
@Documented
public @interface PokemonRule {

    String message() default INVALID_POKEMON_GENERIC;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation;


import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.EvolutionRuleImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.EVOLUTION_GENERIC;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {EvolutionRuleImpl.class})
@Documented
public @interface EvolutionRule {

    String message() default EVOLUTION_GENERIC;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

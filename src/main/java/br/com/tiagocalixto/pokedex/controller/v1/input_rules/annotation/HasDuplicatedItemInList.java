package br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation;


import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl.HasDuplicatedItemInListImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.DUPLICATED_ITEM_GENERIC;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {HasDuplicatedItemInListImpl.class})
@Documented
public @interface HasDuplicatedItemInList {

    String message() default DUPLICATED_ITEM_GENERIC;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

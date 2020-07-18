package br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups;

import javax.validation.GroupSequence;

@GroupSequence({FirstStepValidation.class, SecondStepValidation.class, ThirdStepValidation.class,
        ForthStepValidation.class})
public interface ValidationOrder {
}

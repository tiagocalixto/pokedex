package br.com.tiagocalixto.pokedex.controller.input_rules.groups;

import javax.validation.GroupSequence;

@GroupSequence({FirstStepValidation.class, SecondStepValidation.class, ThirdStepValidation.class})
public interface ValidationOrder {
}

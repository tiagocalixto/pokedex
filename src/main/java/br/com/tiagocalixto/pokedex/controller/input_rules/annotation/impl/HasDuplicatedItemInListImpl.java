package br.com.tiagocalixto.pokedex.controller.input_rules.annotation.impl;

import br.com.tiagocalixto.pokedex.controller.input_rules.annotation.HasDuplicatedItemInList;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class HasDuplicatedItemInListImpl implements ConstraintValidator<HasDuplicatedItemInList, List<?>> {


    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {

        try {
            return list.stream().distinct().count() == list.size();

        } catch (Exception ex) {
            return false;
        }
    }

}

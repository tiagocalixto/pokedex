package br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl;

import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.HasDuplicatedItemInList;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class HasDuplicatedItemInListImpl implements ConstraintValidator<HasDuplicatedItemInList, List<?>> {


    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {


        if (list == null || list.isEmpty())
            return true;

        return list.stream().distinct().count() == list.size();

    }

}

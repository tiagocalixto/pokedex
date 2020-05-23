package br.com.tiagocalixto.pokedex.controller.input_rules.annotation.impl;

import br.com.tiagocalixto.pokedex.controller.dto.pokemon.PokemonAbbreviatedDto;
import br.com.tiagocalixto.pokedex.controller.input_rules.annotation.PokemonAbbreviatedRule;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.INVALID_NAME_OR_NUMBER;
import static br.com.tiagocalixto.pokedex.infra.util.Constant.NUMBER_INVALID_CHAR;

@Component
public class PokemonAbbreviatedRuleImpl implements ConstraintValidator<PokemonAbbreviatedRule, PokemonAbbreviatedDto> {


    @Override
    public boolean isValid(PokemonAbbreviatedDto abbreviated, ConstraintValidatorContext context) {

        try {

            if((abbreviated.getNumber() == null || abbreviated.getNumber() <= 0L)){
                if((abbreviated.getName() == null || abbreviated.getName().equalsIgnoreCase(Strings.EMPTY))){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(INVALID_NAME_OR_NUMBER).addConstraintViolation();
                    return false;
                }
            }

            return true;

        } catch (Exception ex) {
            return false;
        }
    }
}
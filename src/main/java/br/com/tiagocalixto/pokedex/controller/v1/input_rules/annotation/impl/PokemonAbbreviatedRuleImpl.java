package br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonAbbreviatedDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.PokemonAbbreviatedRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.INVALID_NAME_OR_NUMBER;

@Slf4j
@Component
public class PokemonAbbreviatedRuleImpl implements ConstraintValidator<PokemonAbbreviatedRule, PokemonAbbreviatedDto> {


    @Override
    public boolean isValid(PokemonAbbreviatedDto abbreviated, ConstraintValidatorContext context) {


        if ((abbreviated.getNumber() == null || abbreviated.getNumber() <= 0L) &&
                (abbreviated.getName() == null || abbreviated.getName().equalsIgnoreCase(Strings.EMPTY))) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(INVALID_NAME_OR_NUMBER).addConstraintViolation();
            log.info(INVALID_NAME_OR_NUMBER);
            return false;
        }

        return true;
    }
}

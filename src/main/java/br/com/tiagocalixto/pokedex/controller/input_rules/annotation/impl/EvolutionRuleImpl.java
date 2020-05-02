package br.com.tiagocalixto.pokedex.controller.input_rules.annotation.impl;

import br.com.tiagocalixto.pokedex.controller.dto.pokemon.PokemonEvolutionDto;
import br.com.tiagocalixto.pokedex.controller.input_rules.annotation.EvolutionRule;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Component
public class EvolutionRuleImpl implements ConstraintValidator<EvolutionRule, PokemonEvolutionDto> {


    @Override
    public boolean isValid(PokemonEvolutionDto evolution, ConstraintValidatorContext context) {

        try {
            switch (evolution.getTrigger()) {

                case LEVEL_UP:
                    if (evolution.getLevel() == null || evolution.getLevel().equals(0L)) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(EVOLUTION_LEVEL_IS_REQUIRED).addConstraintViolation();
                        return false;
                    } else if (evolution.getLevel() < 1L || evolution.getLevel() > 100) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(INVALID_LEVEL_RANGE).addConstraintViolation();
                        return false;
                    }
                    return true;

                case USE_ITEM:
                    if (evolution.getItem() == null) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(EVOLUTION_ITEM_IS_REQUIRED).addConstraintViolation();
                        return false;
                    }
                    return true;

                default:
                    return true;
            }

        } catch (Exception ex) {
            return false;
        }
    }
}

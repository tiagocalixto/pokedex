package br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.impl;

import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.annotation.PokemonRule;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;

@Slf4j
@Component
public class PokemonRuleImpl implements ConstraintValidator<PokemonRule, PokemonDto> {


    @Override
    public boolean isValid(PokemonDto pokemon, ConstraintValidatorContext context) {

        try {

            if (!isWeaknessValid(pokemon)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(WEAKNESS_CANT_BE_TYPE).addConstraintViolation();
                log.info(WEAKNESS_CANT_BE_TYPE);
                return false;
            }

            if (!isEvolveToValid(pokemon)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(EVOLVE_TO_DUPLICATED).addConstraintViolation();
                log.info(EVOLVE_TO_DUPLICATED);
                return false;
            }

            if(!isMoveValid(pokemon)){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(MOVE_IS_DUPLICATE).addConstraintViolation();
                log.info(MOVE_IS_DUPLICATE);
                return false;
            }

            if(evolveToAndPokemonIsTheSame(pokemon)){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(POKEMON_EVOLVES_TO_HIMSELF).addConstraintViolation();
                log.info(POKEMON_EVOLVES_TO_HIMSELF);
                return false;
            }

            if(evolvedFromAndPokemonIsTheSame(pokemon)){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(POKEMON_EVOLVED_FROM_HIMSELF).addConstraintViolation();
                log.info(POKEMON_EVOLVED_FROM_HIMSELF);
                return false;
            }

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    private boolean isWeaknessValid(PokemonDto pokemon) {

        if (pokemon.getWeakness() == null || pokemon.getWeakness().isEmpty())
            return true;

        return pokemon.getWeakness().stream()
                .noneMatch(item -> pokemon.getType().stream().map(TypeDto::getDescription)
                        .collect(Collectors.toList()).contains(item.getDescription()));
    }

    private boolean isEvolveToValid(PokemonDto pokemon) {

        if (pokemon.getEvolveTo() == null || pokemon.getEvolveTo().isEmpty())
            return true;

        return pokemon.getEvolveTo().stream()
                .map(item -> item.getPokemon().getName().toUpperCase().trim())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()).size() == pokemon.getEvolveTo().stream()
                .map(item -> item.getPokemon().getName().toUpperCase().trim())
                .filter(Objects::nonNull)
                .collect(Collectors.toList()).size() &&
                pokemon.getEvolveTo().stream()
                        .map(item -> item.getPokemon().getNumber())
                        .filter(Objects::nonNull)
                        .filter(item -> item > 0L)
                        .collect(Collectors.toSet()).size() == pokemon.getEvolveTo().stream()
                        .map(item -> item.getPokemon().getNumber())
                        .filter(Objects::nonNull)
                        .filter(item -> item > 0L)
                        .collect(Collectors.toList()).size();
    }

    private boolean isMoveValid(PokemonDto pokemon) {

        if (pokemon.getMove() == null || pokemon.getMove().isEmpty())
            return true;

        return pokemon.getMove().stream()
                .map(item -> item.getMove().getDescription())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()).size() == pokemon.getMove().size();
    }

    private boolean evolveToAndPokemonIsTheSame(PokemonDto pokemon){

        if(pokemon.getEvolveTo() == null || pokemon.getEvolveTo().isEmpty())
            return false;

        return pokemon.getEvolveTo().stream()
                .anyMatch(item -> Util.phoneticStringsMatches(item.getPokemon().getName(), pokemon.getName()) ||
                        item.getPokemon().getNumber().equals(pokemon.getNumber()));
    }

    private boolean evolvedFromAndPokemonIsTheSame(PokemonDto pokemon){

        if (pokemon.getEvolvedFrom() == null)
            return false;

        return pokemon.getEvolvedFrom().getPokemon().getNumber().equals(pokemon.getNumber()) ||
               Util.phoneticStringsMatches(pokemon.getEvolvedFrom().getPokemon().getName(),
                       pokemon.getName());
    }
}

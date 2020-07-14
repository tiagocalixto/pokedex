package br.com.tiagocalixto.pokedex.use_case.pokemon.persist.impl;

import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonIncorrectTypeException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonMoveIncorrectException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonNameIncorrectException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.persist.PersistUseCase;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.INTEGRATION_NATIONAL_DEX;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_PERSIST_ABSTRACT;

@Slf4j
@Getter
@Service(POKEMON_PERSIST_ABSTRACT)
public abstract class PokemonPersistAbstractUseCase implements PersistUseCase<Pokemon> {

    //<editor-fold: properties>
    private PokemonMediatorUseCase mediator;
    private FindOneByIdIntegrationPort<Pokemon> nationalDex;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonPersistAbstractUseCase(@Lazy PokemonMediatorUseCase mediator,
                                         @Qualifier(INTEGRATION_NATIONAL_DEX)
                                                 FindOneByIdIntegrationPort<Pokemon> nationalDex) {

        this.mediator = mediator;
        this.nationalDex = nationalDex;
    }
    //</editor-fold>


    protected Pokemon verifyPokemonInfo(Pokemon pokemon) {

        log.info("Verify pokemon before persist {}", pokemon);

        Pokemon pokemonNationalDex = nationalDex.findById(pokemon.getNumber())
                .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

        verifyName(pokemon, pokemonNationalDex);
        verifyType(pokemon, pokemonNationalDex);
        verifyMove(pokemon, pokemonNationalDex);
        pokemon.setEvolvedFrom(mediator.associateOrInsertEvolvedFrom(pokemon));
        pokemon.setEvolveTo(mediator.associateOrInsertEvolveTo(pokemon));

        log.info("Pokemon successfully verified");
        return pokemon;
    }

    private void verifyName(Pokemon pokemon, Pokemon pokemonNationalDex) {

        log.info("verify pokemon name {} ", pokemon.getName());

        if (!Util.phoneticStringsMatches(pokemon.getName(), pokemonNationalDex.getName())) {
            log.info(POKEMON_INCORRECT_NAME);
            throw new PokemonNameIncorrectException(POKEMON_INCORRECT_NAME);
        }

        log.info("Pokemon name successfully verified");
    }

    private void verifyType(Pokemon pokemon, Pokemon pokemonNationalDex) {

        log.info("verify pokemon type {} ", pokemon.getType());

        List<Type> dontBelongs = pokemon.getType().stream()
                .filter(item -> !pokemonNationalDex.getType().stream()
                        .map(Type::getDescription)
                        .collect(Collectors.toList()).contains(item.getDescription()))
                .collect(Collectors.toList());

        if (!dontBelongs.isEmpty()) {
            String message = POKEMON_INCORRECT_TYPE + " - (" +
                    dontBelongs.stream().map(item -> item.getDescription() + " ").toString() + ")";

            log.info(message);
            throw new PokemonIncorrectTypeException(message);
        }

        log.info("Pokemon type successfully verified");
    }

    private void verifyMove(Pokemon pokemon, Pokemon pokemonNationalDex) {

        log.info("verify pokemon move {} ", pokemon.getMove());

        if (!pokemon.getMove().isEmpty()) {

            List<PokemonMove> dontBelongs = pokemon.getMove().stream()
                    .filter(item -> !pokemonNationalDex.getMove().stream()
                            .map(i -> Util.getPhoneticString(i.getMove().getDescription()))
                            .collect(Collectors.toList())
                            .contains(Util.getPhoneticString(item.getMove()
                                    .getDescription().replace(" ", "-"))))
                    .collect(Collectors.toList());

            if (!dontBelongs.isEmpty()) {
                String message = POKEMON_INCORRECT_MOVE + " - (" +
                        dontBelongs.stream().map(item -> item.getMove().getDescription() + " ").toString() + ")";

                log.info(message);
                throw new PokemonMoveIncorrectException(message);
            }
        }

        log.info("Pokemon move successfully verified");
    }
}

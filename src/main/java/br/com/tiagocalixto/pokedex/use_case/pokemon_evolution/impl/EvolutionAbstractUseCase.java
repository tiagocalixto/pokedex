package br.com.tiagocalixto.pokedex.use_case.pokemon_evolution.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.NATIONAL_DEX_UNAVAILABLE;
import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_EVOLUTION_ABSTRACT_USE_CASE;

@Getter
@Slf4j
@Service(POKEMON_EVOLUTION_ABSTRACT_USE_CASE)
public abstract class EvolutionAbstractUseCase {

    //<editor-fold: properties>
    @Value("${pokemon.min.number}")
    private Long pokemonMinNumber;

    @Value("${pokemon.max.number}")
    private Long pokemonMaxNumber;

    private FindOneByIdIntegrationPort<Pokemon> nationalDex;
    private PokemonMediatorUseCase mediator;
    //</editor-fold>

    //<editor-fold: constructor>
    public EvolutionAbstractUseCase(@Qualifier("NationalDex") FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                    @Lazy PokemonMediatorUseCase mediator) {

        this.nationalDex = nationalDex;
        this.mediator = mediator;
    }
    //</editor-fold>


    protected Pokemon associateOrInsert(Pokemon pokemon) {

        log.info("verify if pokemon already exists in database, if not insert - {}", pokemon);

        if (mediator.pokemonExistsByNumber(pokemon.getNumber())) {

            log.info("Pokemon already exists on data base");
            Pokemon evolution = mediator.pokemonFindByNumber(pokemon.getNumber());
            preparePokemonEvolution(evolution);
            return evolution;
        }

        log.info("Pokemon not exists in data base, will be insert based on national dex register");
        Pokemon fromNationalDex = nationalDex.findById(pokemon.getNumber())
                .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

        preparePokemonEvolution(fromNationalDex);
        fromNationalDex.setId(null);
        return mediator.save(fromNationalDex);
    }

    private void preparePokemonEvolution(Pokemon pokemon){

        pokemon.setEvolvedFrom(null);
        pokemon.setEvolveTo(Collections.emptyList());
        pokemon.setMove(Collections.emptyList());
    }

    protected boolean pokemonBelongsToRange(Pokemon pokemon) {

        return pokemon.getNumber() >= pokemonMinNumber && pokemon.getNumber() <= pokemonMaxNumber;
    }

    protected boolean isCorrectEvolution(Pokemon pokemon, Pokemon pokemonNationalDex) {

        return pokemon.getNumber().equals(pokemonNationalDex.getNumber()) ||
                Util.phoneticStringsMatches(pokemon.getName(), pokemonNationalDex.getName());
    }
}

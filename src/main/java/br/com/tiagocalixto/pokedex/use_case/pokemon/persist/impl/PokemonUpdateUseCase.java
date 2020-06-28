package br.com.tiagocalixto.pokedex.use_case.pokemon.persist.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.CantChangeNameOnUpdateException;
import br.com.tiagocalixto.pokedex.infra.exception.CantChangeNumberOnUpdateException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.UpdateRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Slf4j
@Service(POKEMON_UPDATE_USE_CASE)
public class PokemonUpdateUseCase extends PokemonPersistAbstractUseCase {

    //<editor-fold: properties>
    private UpdateRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonUpdateUseCase(@Lazy PokemonMediatorUseCase mediator,
                                @Qualifier("NationalDex")
                                      FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                @Qualifier("PokemonRepositorySql")
                                        UpdateRepositoryPort<Pokemon> repository) {
        super(mediator, nationalDex);
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pokemon execute(Pokemon pokemon) {

        log.info("Update pokemon {}", pokemon);

        Pokemon founded = super.getMediator().pokemonFindById(pokemon.getId());
        verifyPokemonOnUpdate(pokemon, founded);
        pokemon = super.verifyPokemonInfo(pokemon);
        Pokemon updated = repository.update(pokemon);

        log.info("Pokemon successfully updated {}", updated);
        return updated;
    }

    private void verifyPokemonOnUpdate(Pokemon pokemon, Pokemon pokemonDb){

        if(!Util.phoneticStringsMatches(pokemon.getName(), pokemonDb.getName())){
            log.info(CANT_CHANGE_NAME_ON_UPDATE);
            throw new CantChangeNameOnUpdateException(CANT_CHANGE_NAME_ON_UPDATE);
        }

        if(!pokemon.getNumber().equals(pokemonDb.getNumber())){
            log.info(CANT_CHANGE_NUMBER_ON_UPDATE);
            throw new CantChangeNumberOnUpdateException(CANT_CHANGE_NUMBER_ON_UPDATE);
        }
    }
}

package br.com.tiagocalixto.pokedex.use_case.business.pokemon.persist.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.CantChangeNameOnUpdateException;
import br.com.tiagocalixto.pokedex.infra.exception.CantChangeNumberOnUpdateException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.UpdateRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.CANT_CHANGE_NAME_ON_UPDATE;
import static br.com.tiagocalixto.pokedex.infra.constant.Constant.CANT_CHANGE_NUMBER_ON_UPDATE;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.*;

@Slf4j
@Service(POKEMON_UPDATE_USE_CASE)
public class PokemonUpdateUseCase extends PokemonPersistAbstractUseCase {

    //<editor-fold: properties>
    private UpdateRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonUpdateUseCase(@Lazy PokemonMediatorUseCase mediator,
                                @Qualifier(INTEGRATION_NATIONAL_DEX)
                                        FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                @Qualifier(POKEMON_REPOSITORY_SQL)
                                        UpdateRepositoryPort<Pokemon> repository) {
        super(mediator, nationalDex);
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public Pokemon execute(Pokemon pokemon) {

        log.info("Update pokemon {}", pokemon);

        Pokemon founded = super.getMediator().pokemonFindById(pokemon.getId());
        verifyPokemonOnUpdate(pokemon, founded);
        pokemon = super.verifyPokemonInfo(pokemon);
        Pokemon updated = repository.update(pokemon);

        log.info("Pokemon successfully updated {}", updated);
        return updated;
    }

    private void verifyPokemonOnUpdate(Pokemon pokemon, Pokemon pokemonDb) {

        if (!Util.phoneticStringsMatches(pokemon.getName(), pokemonDb.getName())) {
            log.info(CANT_CHANGE_NAME_ON_UPDATE);
            throw new CantChangeNameOnUpdateException(CANT_CHANGE_NAME_ON_UPDATE);
        }

        if (!pokemon.getNumber().equals(pokemonDb.getNumber())) {
            log.info(CANT_CHANGE_NUMBER_ON_UPDATE);
            throw new CantChangeNumberOnUpdateException(CANT_CHANGE_NUMBER_ON_UPDATE);
        }
    }
}

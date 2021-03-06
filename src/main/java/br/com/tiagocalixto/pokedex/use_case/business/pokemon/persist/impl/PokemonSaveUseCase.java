package br.com.tiagocalixto.pokedex.use_case.business.pokemon.persist.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonAlreadyExistsException;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.POKEMON_ALREADY_EXISTS;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.*;

@Slf4j
@Service(POKEMON_SAVE_USE_CASE)
public class PokemonSaveUseCase extends PokemonPersistAbstractUseCase {

    //<editor-fold: properties>
    private InsertRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonSaveUseCase(@Lazy PokemonMediatorUseCase mediator,
                              @Qualifier(INTEGRATION_NATIONAL_DEX)
                                      FindOneByIdIntegrationPort<Pokemon> nationalDex,
                              @Qualifier(POKEMON_REPOSITORY_SQL)
                                      InsertRepositoryPort<Pokemon> repository) {
        super(mediator, nationalDex);
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public Pokemon execute(Pokemon pokemon) {

        log.info("Save pokemon {}", pokemon);

        if (super.getMediator().pokemonExistsByNumber(pokemon.getNumber())) {
            log.info(POKEMON_ALREADY_EXISTS + pokemon.getNumber());
            throw new PokemonAlreadyExistsException(POKEMON_ALREADY_EXISTS + pokemon.getNumber());
        }

        pokemon = super.verifyPokemonInfo(pokemon);
        Pokemon saved = repository.insert(pokemon);

        log.info("Pokemon successfully saved {}", saved);
        return saved;
    }
}

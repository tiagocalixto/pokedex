package br.com.tiagocalixto.pokedex.use_case.pokemon.persist.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonAlreadyExistsException;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_ALREADY_EXISTS;
import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_SAVE_USE_CASE;

@Slf4j
@Service(POKEMON_SAVE_USE_CASE)
public class PokemonSaveUseCase extends PokemonPersistAbstractUseCase {

    //<editor-fold: properties>
    private InsertRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonSaveUseCase(@Lazy PokemonMediatorUseCase mediator,
                              @Qualifier("NationalDex")
                                      FindOneByIdIntegrationPort<Pokemon> nationalDex,
                              @Qualifier("PokemonRepositorySql")
                                      InsertRepositoryPort<Pokemon> repository) {
        super(mediator, nationalDex);
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

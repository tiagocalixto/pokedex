package br.com.tiagocalixto.pokedex.use_case.business.pokemon.delete.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source.delete.DeleteRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.delete.DeleteByIdUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_DELETE_USE_CASE;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Service(POKEMON_DELETE_USE_CASE)
public class PokemonDeleteByIdUseCase implements DeleteByIdUseCase {

    //<editor-fold: properties>
    private DeleteRepositoryPort<Pokemon> repository;
    private PokemonMediatorUseCase mediator;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonDeleteByIdUseCase(@Qualifier(POKEMON_REPOSITORY_SQL)
                                            DeleteRepositoryPort<Pokemon> repository,
                                    @Lazy PokemonMediatorUseCase mediator) {
        this.repository = repository;
        this.mediator = mediator;
    }
    //</editor-fold>


    @Override
    public void execute(Long id) {

        log.info("Delete pokemon With id {}", id);

        Pokemon pokemon = mediator.pokemonFindById(id);
        repository.delete(pokemon);

        log.info("Pokemon with id {} successfully deleted", id);
    }
}

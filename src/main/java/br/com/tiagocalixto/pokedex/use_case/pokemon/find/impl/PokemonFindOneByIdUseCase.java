package br.com.tiagocalixto.pokedex.use_case.pokemon.find.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source.find.FindOneByIdRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindOneByIdUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_NOT_FOUND_BY_ID;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_FIND_ONE_BY_ID_USE_CASE;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Service(POKEMON_FIND_ONE_BY_ID_USE_CASE)
public class PokemonFindOneByIdUseCase implements FindOneByIdUseCase<Pokemon> {

    //<editor-fold: properties>
    private FindOneByIdRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonFindOneByIdUseCase(@Qualifier(POKEMON_REPOSITORY_SQL)
                                             FindOneByIdRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public Pokemon execute(Long id) {

        log.info("Find pokemon by id {}", id);

        Pokemon pokemon = repository.findById(id)
                .orElseGet(() -> {
                    log.info(POKEMON_NOT_FOUND_BY_ID + id);
                    throw new EntityNotFoundException(POKEMON_NOT_FOUND_BY_ID + id);
                });

        log.info("Pokemon founded {}", pokemon);
        return pokemon;
    }
}

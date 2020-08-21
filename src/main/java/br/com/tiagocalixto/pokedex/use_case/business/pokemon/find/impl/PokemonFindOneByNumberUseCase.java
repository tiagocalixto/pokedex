package br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source.find.FindOneByNumberRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindOneByNumberUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.POKEMON_NOT_FOUND_BY_NUMBER;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_FIND_ONE_BY_NUMBER_CASE;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Service(POKEMON_FIND_ONE_BY_NUMBER_CASE)
public class PokemonFindOneByNumberUseCase implements FindOneByNumberUseCase {

    //<editor-fold: properties>
    private FindOneByNumberRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonFindOneByNumberUseCase(@Qualifier(POKEMON_REPOSITORY_SQL)
                                                 FindOneByNumberRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public Pokemon execute(Long number) {

        log.info("Find pokemon by number {}", number);

        Pokemon pokemon = repository.findByNumber(number)
                .orElseGet(() -> {
                    log.info(POKEMON_NOT_FOUND_BY_NUMBER + number);
                    throw new EntityNotFoundException(POKEMON_NOT_FOUND_BY_NUMBER + number);
                });

        log.info("Pokemon founded {}", pokemon);
        return pokemon;
    }
}

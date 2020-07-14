package br.com.tiagocalixto.pokedex.use_case.pokemon.find.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source.find.FindAllByNameRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindAllByNameUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_NOT_FOUND_BY_NAME;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_FIND_ALL_BY_NAME_USE_CASE;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Service(POKEMON_FIND_ALL_BY_NAME_USE_CASE)
public class PokemonFindAllByNameUseCase implements FindAllByNameUseCase<Pokemon> {

    //<editor-fold: properties>
    private FindAllByNameRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonFindAllByNameUseCase(@Qualifier(POKEMON_REPOSITORY_SQL)
                                               FindAllByNameRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public List<Pokemon> execute(String name) {

        log.info("Find pokemon by name {}", name);
        List<Pokemon> pokemon = repository.findAllByName(name);

        if (pokemon.isEmpty()) {
            log.info(POKEMON_NOT_FOUND_BY_NAME + name);
            throw new EntityNotFoundException(POKEMON_NOT_FOUND_BY_NAME);
        }

        log.info("{} pokemon(s) founded by name {}", pokemon.size(), name);
        return pokemon;
    }
}

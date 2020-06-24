package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.FindOneByIdRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.FindOneByIdUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_NOT_FOUND_BY_ID;

@Slf4j
@Component("PokemonFindByIdUseCase")
public class PokemonFindByIdUseCase implements FindOneByIdUseCase<Pokemon> {

    private FindOneByIdRepositoryPort<Pokemon> repository;

    public PokemonFindByIdUseCase(@Qualifier("PokemonRepositorySql")
                                          FindOneByIdRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }


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

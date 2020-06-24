package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.FindAllByNameRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.FindAllByNameUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component("PokemonFindAllByNameUseCase")
public class PokemonFindAllByNameUseCase implements FindAllByNameUseCase<Pokemon> {

    private FindAllByNameRepositoryPort<Pokemon> repository;

    public PokemonFindAllByNameUseCase(@Qualifier("PokemonRepositorySql")
                                               FindAllByNameRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }


    @Override
    public List<Pokemon> execute(String name) {

        log.info("Find pokemon by name {}", name);
        List<Pokemon> pokemon = repository.findAllByName(name);

        if (pokemon.isEmpty()) {
            log.info("Any pokemon founded by name {}", name);
            return Collections.emptyList();
        }

        log.info("{} pokemon(s) founded by name {}", pokemon.size(), name);
        return pokemon;
    }
}

package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.ports.data_source_ports.ExistsByIdRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.ExistsByIdUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component("PokemonFindByIdUseCase")
public class PokemonExistsByIdUseCase implements ExistsByIdUseCase {

    private ExistsByIdRepositoryPort repository;

    public PokemonExistsByIdUseCase(@Qualifier("PokemonRepositorySql")
                                            ExistsByIdRepositoryPort repository) {
        this.repository = repository;
    }


    @Override
    public Boolean execute(Long id) {

        log.info("Verify if pokemon exists by id {}", id);
        boolean pokemon = repository.isExistsById(id);

        if(!pokemon) {
            log.info("Pokemon with id {} not exists", id);
            return false;
        }

        log.info("Pokemon with id {} founded", id);
        return true;
    }
}

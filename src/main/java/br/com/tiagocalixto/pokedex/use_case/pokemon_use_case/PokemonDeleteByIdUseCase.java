package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.DeleteRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.DeleteByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("PokemonDeleteByIdUseCase")
public class PokemonDeleteByIdUseCase implements DeleteByIdUseCase {

    private DeleteRepositoryPort<Pokemon> repository;
    private PokemonMediator mediator;

    public PokemonDeleteByIdUseCase(@Qualifier("PokemonRepositorySql")
                                            DeleteRepositoryPort<Pokemon> repository,
                                    PokemonMediator mediator) {
        this.repository = repository;
        this.mediator = mediator;
    }


    @Transactional
    @Override
    public void execute(Long id) {

        log.info("Delete pokemon With id {}", id);

        Pokemon pokemon = mediator.pokemonFindById(id);
        repository.delete(pokemon);

        log.info("Pokemon with id {} successfully deleted", id);
    }
}

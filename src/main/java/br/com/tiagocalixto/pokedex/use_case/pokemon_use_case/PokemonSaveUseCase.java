package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.PersistUseCase;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("PokemonSaveUseCase")
public class PokemonSaveUseCase extends PokemonPersistAbstract {

    private InsertRepositoryPort<Pokemon> repository;

    public PokemonSaveUseCase(PokemonMediator mediator,
                              @Qualifier("NationalDex")
                                      FindOneByIdIntegrationPort<Pokemon> nationalDex,
                              @Qualifier("PokemonRepositorySql")
                                      InsertRepositoryPort<Pokemon> repository) {
        super(mediator, nationalDex);
        this.repository = repository;
    }


    @Transactional
    @Override
    public Pokemon execute(Pokemon pokemon) {

        log.info("Save pokemon {}", pokemon);

        pokemon = super.verifyPokemonInfo(pokemon);
        Pokemon saved = repository.insert(pokemon);

        log.info("Pokemon successfully saved {}", saved);
        return saved;
    }
}

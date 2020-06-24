package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.UpdateRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("PokemonUpdateUseCase")
public class PokemonUpdateUseCase extends PokemonPersistAbstract {

    private UpdateRepositoryPort<Pokemon> repository;

    public PokemonUpdateUseCase(PokemonMediator mediator,
                                @Qualifier("NationalDex")
                                      FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                @Qualifier("PokemonRepositorySql")
                                        UpdateRepositoryPort<Pokemon> repository) {
        super(mediator, nationalDex);
        this.repository = repository;
    }


    @Transactional
    @Override
    public Pokemon execute(Pokemon pokemon) {

        log.info("Update pokemon {}", pokemon);

        super.getMediator().pokemonFindById(pokemon.getNumber());
        pokemon = super.verifyPokemonInfo(pokemon);
        Pokemon updated = repository.update(pokemon);

        log.info("Pokemon successfully updated {}", updated);
        return updated;
    }
}

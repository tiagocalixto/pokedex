package br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonEvolutionIncorrectException;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.AssociateOrInsertEvolveToUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.INTEGRATION_NATIONAL_DEX;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_ASSOCIATE_EVOLVE_TO_USE_CASE;

@Slf4j
@Service(POKEMON_ASSOCIATE_EVOLVE_TO_USE_CASE)
public class EvolutionAssociateOrInsertEvolveToUseCase extends EvolutionAbstractUseCase
        implements AssociateOrInsertEvolveToUseCase {

    //<editor-fold: constructor>
    public EvolutionAssociateOrInsertEvolveToUseCase(@Qualifier(INTEGRATION_NATIONAL_DEX)
                                                             FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                                     @Lazy PokemonMediatorUseCase mediator) {
        super(nationalDex, mediator);
    }
    //</editor-fold>


    @Override
    public List<PokemonEvolution> execute(Pokemon pokemon) {

        log.info("Associate or insert evolve to - evolve to: {}", pokemon.getEvolveTo());

        if (!pokemon.getEvolveTo().isEmpty()) {

            Pokemon pokemonNationalDex = super.getNationalDex().findById(pokemon.getNumber())
                    .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

            pokemonNationalDex.setEvolveTo(pokemonNationalDex.getEvolveTo().stream()
                    .filter(item -> super.pokemonBelongsToRange(item.getPokemon()))
                    .collect(Collectors.toList()));

            if (pokemonNationalDex.getEvolveTo().isEmpty()) {
                log.info(POKEMON_DONT_EVOLVES_TO + " - " + POKEMON_CONSIDER);

                throw new PokemonEvolutionIncorrectException(POKEMON_DONT_EVOLVES_TO + " - " +
                        POKEMON_CONSIDER);
            }

            pokemon.getEvolveTo().forEach(item -> {

                Pokemon founded = pokemonNationalDex.getEvolveTo().stream()
                        .filter(i -> isCorrectEvolution(item.getPokemon(), i.getPokemon()))
                        .map(PokemonEvolution::getPokemon)
                        .findFirst().orElse(null);

                if (founded == null) {
                    log.info(POKEMON_INCORRECT_EVOLVE_TO
                            + "(number: " + item.getPokemon().getNumber() + ", name: " + item.getPokemon().getName()
                            + " - " + POKEMON_CONSIDER);

                    throw new PokemonEvolutionIncorrectException(POKEMON_INCORRECT_EVOLVE_TO
                            + "(number: " + item.getPokemon().getNumber() + ", name: " + item.getPokemon().getName()
                            + " - " + POKEMON_CONSIDER);
                }

                item.setPokemon(super.associateOrInsert(founded));
            });
        }

        return pokemon.getEvolveTo();
    }
}

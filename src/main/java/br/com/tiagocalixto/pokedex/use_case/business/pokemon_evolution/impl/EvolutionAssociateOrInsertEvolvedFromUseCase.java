package br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonEvolutionIncorrectException;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.AssociateOrInsertEvolvedFromUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.INTEGRATION_NATIONAL_DEX;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_ASSOCIATE_EVOLVED_FROM_USE_CASE;

@Slf4j
@Service(POKEMON_ASSOCIATE_EVOLVED_FROM_USE_CASE)
public class EvolutionAssociateOrInsertEvolvedFromUseCase extends EvolutionAbstractUseCase
        implements AssociateOrInsertEvolvedFromUseCase {

    //<editor-fold: constructor>
    public EvolutionAssociateOrInsertEvolvedFromUseCase(@Qualifier(INTEGRATION_NATIONAL_DEX)
                                                                FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                                        @Lazy PokemonMediatorUseCase mediator) {
        super(nationalDex, mediator);
    }
    //</editor-fold>


    @Override
    public PokemonEvolution execute(Pokemon pokemon) {

        log.info("Associate or insert evolved from - evolved from: {}", pokemon.getEvolvedFrom());

        if (pokemon.getEvolvedFrom() != null) {

            Pokemon pokemonNationalDex = super.getNationalDex().findById(pokemon.getNumber())
                    .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

            if (!super.pokemonBelongsToRange(pokemonNationalDex.getEvolvedFrom().getPokemon())) {
                pokemonNationalDex.setEvolvedFrom(null);
            }

            if (pokemonNationalDex.getEvolvedFrom() == null) {
                log.info(POKEMON_DONT_EVOLVED_FROM + " - " + POKEMON_CONSIDER);

                throw new PokemonEvolutionIncorrectException(POKEMON_DONT_EVOLVED_FROM + " - " +
                        POKEMON_CONSIDER);
            }

            if (!super.isCorrectEvolution(pokemon.getEvolvedFrom().getPokemon(),
                    pokemonNationalDex.getEvolvedFrom().getPokemon())) {
                log.info(POKEMON_INCORRECT_EVOLVED_FROM + " - " +
                        POKEMON_CONSIDER);

                throw new PokemonEvolutionIncorrectException(POKEMON_INCORRECT_EVOLVED_FROM + " - " +
                        POKEMON_CONSIDER);
            }

            pokemon.getEvolvedFrom().setPokemon(
                    super.associateOrInsert(pokemonNationalDex.getEvolvedFrom().getPokemon()));
        }

        return pokemon.getEvolvedFrom();
    }
}

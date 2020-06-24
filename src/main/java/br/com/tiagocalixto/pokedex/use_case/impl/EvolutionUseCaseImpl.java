package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonEvolutionIncorrectException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.EvolutionUseCase;
import br.com.tiagocalixto.pokedex.use_case.FindOneByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.ExistsByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.PersistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Service("EvolutionUseCaseImpl")
public class EvolutionUseCaseImpl implements EvolutionUseCase {

    //<editor-fold: properties>
    private FindOneByIdIntegrationPort<Pokemon> nationalDex;
    private ExistsByIdUseCase pokemonExists;
    private FindOneByIdUseCase<Pokemon> findPokemon;
    private PersistUseCase<Pokemon> savePokemon;

    @Value("${pokemon.min.number}")
    private Long pokemonMinNumber;

    @Value("${pokemon.max.number}")
    private Long pokemonMaxNumber;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public EvolutionUseCaseImpl(@Qualifier("NationalDex") FindOneByIdIntegrationPort<Pokemon> nationalDex,
                                @Lazy @Qualifier("PokemonUseCaseImpl") ExistsByIdUseCase pokemonExists,
                                @Lazy @Qualifier("PokemonUseCaseImpl") FindOneByIdUseCase<Pokemon> findPokemon,
                                @Lazy @Qualifier("PokemonUseCaseImpl") PersistUseCase<Pokemon> savePokemon) {

        this.nationalDex = nationalDex;
        this.pokemonExists = pokemonExists;
        this.findPokemon = findPokemon;
        this.savePokemon = savePokemon;
    }
    //</editor-fold>

    @Override
    public List<PokemonEvolution> verifyEvolveTo(Pokemon pokemon) {

        if (!pokemon.getEvolveTo().isEmpty()) {

            Pokemon pokemonNationalDex = nationalDex.findById(pokemon.getId())
                    .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

            pokemonNationalDex.setEvolveTo(pokemonNationalDex.getEvolveTo().stream()
                    .filter(item -> pokemonBelongsToRange(item.getPokemon()))
                    .collect(Collectors.toList()));

            if (pokemonNationalDex.getEvolveTo().isEmpty()) {
                throw new PokemonEvolutionIncorrectException(POKEMON_DONT_EVOLVES_TO + " - " +
                        POKEMON_CONSIDER);
            }

            pokemon.getEvolveTo().forEach(item -> {

                Pokemon founded = pokemonNationalDex.getEvolveTo().stream()
                        .filter(i -> isCorrectEvolution(item.getPokemon(), i.getPokemon()))
                        .map(PokemonEvolution::getPokemon)
                        .findFirst().orElse(null);

                if (founded == null) {
                    throw new PokemonEvolutionIncorrectException(POKEMON_INCORRECT_EVOLVE_TO
                            + "(number: " + item.getPokemon().getNumber() + ", name: " + item.getPokemon().getName()
                            + " - " + POKEMON_CONSIDER);
                }

                item.setPokemon(associateOrInsert(founded));
            });
        }

        return pokemon.getEvolveTo();
    }

    @Override
    public PokemonEvolution verifyEvolvedFrom(Pokemon pokemon) {

        if (pokemon.getEvolvedFrom() != null) {

            Pokemon pokemonNationalDex = nationalDex.findById(pokemon.getId())
                    .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

            if (pokemonBelongsToRange(pokemonNationalDex.getEvolvedFrom().getPokemon())) {
                pokemonNationalDex.setEvolvedFrom(null);
            }

            if (pokemonNationalDex.getEvolvedFrom() == null) {
                throw new PokemonEvolutionIncorrectException(POKEMON_DONT_EVOLVED_FROM + " - " +
                        POKEMON_CONSIDER);
            }

            if (!isCorrectEvolution(pokemon.getEvolvedFrom().getPokemon(),
                    pokemonNationalDex.getEvolvedFrom().getPokemon())) {
                throw new PokemonEvolutionIncorrectException(POKEMON_INCORRECT_EVOLVED_FROM + " - " +
                        POKEMON_CONSIDER);
            }

            pokemon.getEvolvedFrom().setPokemon(associateOrInsert(pokemonNationalDex.getEvolvedFrom().getPokemon()));
        }

        return pokemon.getEvolvedFrom();
    }

    private Pokemon associateOrInsert(Pokemon pokemon) {

        if (pokemonExists.execute(pokemon.getId())) {

            Pokemon evolution = findPokemon.execute(pokemon.getId());
            evolution.setEvolvedFrom(null);
            evolution.setEvolveTo(Collections.emptyList());
            return evolution;
        }

        Pokemon fromNationalDex = nationalDex.findById(pokemon.getId())
                .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

        fromNationalDex.setEvolvedFrom(null);
        fromNationalDex.setEvolveTo(Collections.emptyList());

        return savePokemon.execute(fromNationalDex);
    }

    private boolean pokemonBelongsToRange(Pokemon pokemon) {

        return pokemon.getNumber() >= pokemonMinNumber && pokemon.getNumber() <= pokemonMaxNumber;
    }

    private boolean isCorrectEvolution(Pokemon pokemon, Pokemon pokemonNationalDex) {

        return !pokemon.getNumber().equals(pokemonNationalDex.getNumber()) ||
                !Util.phoneticStringsMatches(pokemon.getName(), pokemonNationalDex.getName());
    }
}
package br.com.tiagocalixto.pokedex.use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

import java.util.List;

public interface EvolutionUseCase {

    List<PokemonEvolution> verifyEvolveTo(Pokemon pokemon);
    PokemonEvolution verifyEvolvedFrom(Pokemon pokemon);
}

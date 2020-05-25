package br.com.tiagocalixto.pokedex.use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

public interface EvolutionUseCase {

    PokemonEvolution associateOrInsert(PokemonEvolution evolution);
}

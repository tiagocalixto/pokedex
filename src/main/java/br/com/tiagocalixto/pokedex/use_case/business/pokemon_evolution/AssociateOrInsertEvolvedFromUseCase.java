package br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

public interface AssociateOrInsertEvolvedFromUseCase {

    PokemonEvolution execute(Pokemon pokemon);
}

package br.com.tiagocalixto.pokedex.use_case.pokemon_evolution;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

import java.util.List;

public interface AssociateOrInsertEvolveToUseCase {

    List<PokemonEvolution> execute(Pokemon pokemon);
}

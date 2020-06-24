package br.com.tiagocalixto.pokedex.use_case.mediator;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

import java.util.List;

public interface PokemonMediator {

    Pokemon pokemonFindById(Long id);
    List<Pokemon> pokemonFindPageable(int pageNumber);
    List<Pokemon> pokemonFindAllByName(String name);
    Boolean pokemonExistsById(Long id);
    void pokemonDeleteById(Long id);
    Pokemon save(Pokemon pokemon);
    Pokemon update(Pokemon pokemon);
    PokemonEvolution prepareEvolvedFrom(PokemonEvolution pokemonEvolution);
    List<PokemonEvolution> prepareEvolveTo(List<PokemonEvolution> pokemonEvolution);
}

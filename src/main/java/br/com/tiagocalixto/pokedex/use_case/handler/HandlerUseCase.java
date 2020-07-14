package br.com.tiagocalixto.pokedex.use_case.handler;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;

import java.util.List;

public interface HandlerUseCase {

    Pokemon savePokemon(Pokemon pokemon);
    Pokemon updatePokemon(Pokemon pokemon);
    void deletePokemonById(Long id);
    Pokemon findPokemonById(Long id);
    List<Pokemon> findPokemonByName(String name);
    Pokemon findPokemonByNumber(Long number);
    List<Pokemon> findPokemonPageable(int pageNumber);
}

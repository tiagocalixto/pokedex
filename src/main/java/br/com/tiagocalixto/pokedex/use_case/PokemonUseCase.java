package br.com.tiagocalixto.pokedex.use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;

import java.util.List;

public interface PokemonUseCase {

    List<Pokemon> findByName(String name);
}

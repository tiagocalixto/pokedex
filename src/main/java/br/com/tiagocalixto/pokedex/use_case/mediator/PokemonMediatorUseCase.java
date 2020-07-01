package br.com.tiagocalixto.pokedex.use_case.mediator;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

import java.util.List;

public interface PokemonMediatorUseCase {

    void pokemonDeleteById(Long id);
    boolean pokemonExistsByNumber(Long number);
    List<Pokemon> pokemonFindAllByName(String name);
    Pokemon pokemonFindById(Long id);
    Pokemon pokemonFindByNumber(Long number);
    List<Pokemon> pokemonFindPageable(int pageNumber);
    Pokemon save(Pokemon pokemon);
    Pokemon update(Pokemon pokemon);
    PokemonEvolution associateOrInsertEvolvedFrom(Pokemon pokemon);
    List<PokemonEvolution> associateOrInsertEvolveTo(Pokemon pokemon);
}

package br.com.tiagocalixto.pokedex.use_case.mediator;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;

import java.util.List;

public interface PokemonMediatorUseCase {

    boolean pokemonExistsByNumber(Long number);
    Pokemon pokemonFindById(Long id);
    Pokemon pokemonFindByNumber(Long number);
    PokemonEvolution associateOrInsertEvolvedFrom(Pokemon pokemon);
    List<PokemonEvolution> associateOrInsertEvolveTo(Pokemon pokemon);
}

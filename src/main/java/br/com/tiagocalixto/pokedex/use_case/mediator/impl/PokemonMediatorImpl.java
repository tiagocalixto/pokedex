package br.com.tiagocalixto.pokedex.use_case.mediator.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokemonMediatorImpl implements PokemonMediator {
    @Override
    public Pokemon pokemonFindById(Long id) {
        return null;
    }

    @Override
    public List<Pokemon> pokemonFindPageable(int pageNumber) {
        return null;
    }

    @Override
    public List<Pokemon> pokemonFindAllByName(String name) {
        return null;
    }

    @Override
    public Boolean pokemonExistsById(Long id) {
        return null;
    }

    @Override
    public void pokemonDeleteById(Long id) {

    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        return null;
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        return null;
    }

    @Override
    public PokemonEvolution prepareEvolvedFrom(PokemonEvolution pokemonEvolution) {
        return null;
    }

    @Override
    public List<PokemonEvolution> prepareEvolveTo(List<PokemonEvolution> pokemonEvolution) {
        return null;
    }
}

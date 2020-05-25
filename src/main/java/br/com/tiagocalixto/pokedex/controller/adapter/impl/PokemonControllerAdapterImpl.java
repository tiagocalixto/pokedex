package br.com.tiagocalixto.pokedex.controller.adapter.impl;

import br.com.tiagocalixto.pokedex.controller.adapter.PokemonControllerAdapter;
import br.com.tiagocalixto.pokedex.controller.dto.pokemon.PokemonDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokemonControllerAdapterImpl implements PokemonControllerAdapter {


    @Override
    public List<PokemonDto> findByName(String name) {
        return null;
    }

    @Override
    public PokemonDto findByNumber(Long number) {
        return null;
    }

    @Override
    public List<PokemonDto> pageableListPokemon(Integer pageNumber) {
        return null;
    }

    @Override
    public PokemonDto save(PokemonDto pokemon) {
        return null;
    }

    @Override
    public PokemonDto update(PokemonDto pokemon) {
        return null;
    }

    @Override
    public void delete(Long number) {

    }
}

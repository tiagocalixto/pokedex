package br.com.tiagocalixto.pokedex.controller.adapter;

import br.com.tiagocalixto.pokedex.controller.dto.pokemon.PokemonDto;

import java.util.List;

public interface PokemonControllerAdapter {

    PokemonDto findByName(String name);
    PokemonDto findByNumber(Long number);
    List<PokemonDto> pageableListPokemon(Integer pageNumber);
    PokemonDto save(PokemonDto pokemon);
    PokemonDto update(PokemonDto pokemon);
    void delete(Long number);
}

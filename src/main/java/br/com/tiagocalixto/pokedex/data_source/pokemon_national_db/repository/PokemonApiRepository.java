package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository;


import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonApi;

public interface PokemonApiRepository {

    void getPokemonFromNationalDataBase(Integer idPokemon);
    PokemonApi getPokemon(Long number);
}

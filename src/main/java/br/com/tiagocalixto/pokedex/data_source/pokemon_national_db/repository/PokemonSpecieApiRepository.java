package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonSpecieApi;

public interface PokemonSpecieApiRepository {

    void getSpecieFromNationalDataBase(Integer idPokemon);
    PokemonSpecieApi getPokemonSpecie();
}

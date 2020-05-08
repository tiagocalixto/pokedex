package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository;


import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonApi;

import java.util.Optional;

public interface PokemonApiRepository {

    void getPokemonFromNationalDataBase(Integer idPokemon);
    Optional<PokemonApi> getPokemon();
}

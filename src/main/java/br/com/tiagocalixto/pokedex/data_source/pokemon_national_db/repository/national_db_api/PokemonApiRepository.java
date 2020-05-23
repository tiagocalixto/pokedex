package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api;


import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonNationalDb;

import java.util.Optional;

public interface PokemonApiRepository {

    void getPokemonFromNationalDataBase(Integer idPokemon);
    Optional<PokemonNationalDb> getPokemon();
}

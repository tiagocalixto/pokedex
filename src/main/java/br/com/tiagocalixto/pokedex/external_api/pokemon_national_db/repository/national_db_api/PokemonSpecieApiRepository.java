package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.repository.national_db_api;

import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.pokemon.PokemonSpecieNationalDb;

import java.util.Optional;

public interface PokemonSpecieApiRepository {

    void getSpecieFromNationalDataBase(Integer idPokemon);
    Optional<PokemonSpecieNationalDb> getPokemonSpecie();
}

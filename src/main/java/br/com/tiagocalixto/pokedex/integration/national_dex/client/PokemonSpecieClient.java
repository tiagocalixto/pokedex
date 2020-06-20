package br.com.tiagocalixto.pokedex.integration.national_dex.client;

import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonSpecieNationalDexDto;

import java.util.Optional;

public interface PokemonSpecieClient {

    void getSpecieFromNationalDataBase(Integer idPokemon);
    Optional<PokemonSpecieNationalDexDto> getPokemonSpecie();
}

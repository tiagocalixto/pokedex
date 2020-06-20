package br.com.tiagocalixto.pokedex.integration.national_dex.client;


import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;

import java.util.Optional;

public interface PokemonClient {

    void getPokemonFromNationalDataBase(Integer idPokemon);
    Optional<PokemonNationalDexDto> getPokemon();
}

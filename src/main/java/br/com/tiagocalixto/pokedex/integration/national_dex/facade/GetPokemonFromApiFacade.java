package br.com.tiagocalixto.pokedex.integration.national_dex.facade;

import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;

import java.util.Optional;

public interface GetPokemonFromApiFacade {

    Optional<PokemonNationalDexDto> getPokemon(Long id);
}

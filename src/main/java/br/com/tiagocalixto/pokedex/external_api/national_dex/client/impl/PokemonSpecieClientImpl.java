package br.com.tiagocalixto.pokedex.external_api.national_dex.client.impl;

import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.pokemon.PokemonSpecieNationalDexDto;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.evolution_chain.EvolvedFromNationalDexDto;
import br.com.tiagocalixto.pokedex.external_api.national_dex.client.PokemonSpecieClient;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

import java.util.Optional;

public class PokemonSpecieClientImpl implements PokemonSpecieClient {

    private PokemonSpecies species;
    private PokeApi pokeApi;

    public PokemonSpecieClientImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getSpecieFromNationalDataBase(Integer idPokemon) {

        this.species = pokeApi.getPokemonSpecies(idPokemon);
    }

    @Override
    public Optional<PokemonSpecieNationalDexDto> getPokemonSpecie() {

        if (this.species == null)
            return Optional.empty();

        return Optional.of(PokemonSpecieNationalDexDto.builder()
                .evolvedFrom(this.species.getEvolvesFromSpecies() == null ? null :
                        EvolvedFromNationalDexDto.builder()
                                .id(Long.valueOf(this.species.getEvolvesFromSpecies().getId()))
                                .name(this.species.getEvolvesFromSpecies().getName())
                                .build())
                .generation(this.species.getGeneration().getName())
                .idEvolutionChain(Long.valueOf(this.species.getEvolutionChain().getId()))
                .build());
    }
}

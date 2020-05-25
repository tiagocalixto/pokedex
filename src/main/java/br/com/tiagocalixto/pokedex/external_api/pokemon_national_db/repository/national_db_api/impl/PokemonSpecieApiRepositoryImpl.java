package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.repository.national_db_api.impl;

import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.pokemon.PokemonSpecieNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.evolution_chain.EvolutionChainEvolvedFromNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.repository.national_db_api.PokemonSpecieApiRepository;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

import java.util.Optional;

public class PokemonSpecieApiRepositoryImpl implements PokemonSpecieApiRepository {

    private PokemonSpecies species;
    private PokeApi pokeApi;

    public PokemonSpecieApiRepositoryImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getSpecieFromNationalDataBase(Integer idPokemon) {

        this.species = pokeApi.getPokemonSpecies(idPokemon);
    }

    @Override
    public Optional<PokemonSpecieNationalDb> getPokemonSpecie() {

        if (this.species == null)
            return Optional.empty();

        return Optional.of(PokemonSpecieNationalDb.builder()
                .evolvedFrom(this.species.getEvolvesFromSpecies() == null ? null :
                        EvolutionChainEvolvedFromNationalDb.builder()
                                .id(Long.valueOf(this.species.getEvolvesFromSpecies().getId()))
                                .name(this.species.getEvolvesFromSpecies().getName())
                                .build())
                .generation(this.species.getGeneration().getName())
                .idEvolutionChain(Long.valueOf(this.species.getEvolutionChain().getId()))
                .build());
    }
}

package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.impl;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonSpecieApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonSpecieEvolvedFrom;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.PokemonSpecieApiRepository;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PokemonSpecieApiRepositoryImpl implements PokemonSpecieApiRepository {

    private PokemonSpecies species;
    private PokeApi pokeApi;

    @Autowired
    public PokemonSpecieApiRepositoryImpl(PokeApi pokeApi){

        this.pokeApi = pokeApi;
    }


    @Override
    public void getSpecieFromNationalDataBase(Integer idPokemon) {

        this.species = pokeApi.getPokemonSpecies(idPokemon);
    }

    @Override
    public PokemonSpecieApi getPokemonSpecie() {

        return PokemonSpecieApi.builder()
                .evolvedFrom(this.species.getEvolvesFromSpecies() == null ? null :
                        PokemonSpecieEvolvedFrom.builder()
                        .id(Long.valueOf(this.species.getEvolvesFromSpecies().getId()))
                        .name(this.species.getEvolvesFromSpecies().getName())
                        .build())
                .generation(this.species.getGeneration().getName())
                .idEvolutionChain(Long.valueOf(this.species.getEvolutionChain().getId()))
                .build();
    }
}

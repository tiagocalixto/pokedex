package br.com.tiagocalixto.pokedex.controller;

import lombok.Data;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.EvolutionChain;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

@Data
public class threadTest {

    private Pokemon pokemon;
    private PokemonSpecies species;
    private EvolutionChain chain;
    private PokeApi pokeApi = new PokeApiClient();

    public void getPOkemonFromApi(Integer number){


        this.pokemon = pokeApi.getPokemon(number);
    }

    public void getPOkemonSpeciesFromApi(Integer number){


        this.species = pokeApi.getPokemonSpecies(number);
    }

    public void getchainFromApi(Integer number){


        this.chain = pokeApi.getEvolutionChain(number);
    }
}

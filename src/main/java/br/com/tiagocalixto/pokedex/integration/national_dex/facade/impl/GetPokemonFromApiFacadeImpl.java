package br.com.tiagocalixto.pokedex.integration.national_dex.facade.impl;

import br.com.tiagocalixto.pokedex.integration.national_dex.client.EvolutionChainClient;
import br.com.tiagocalixto.pokedex.integration.national_dex.client.PokemonClient;
import br.com.tiagocalixto.pokedex.integration.national_dex.client.PokemonSpecieClient;
import br.com.tiagocalixto.pokedex.integration.national_dex.client.impl.EvolutionChainClientImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.client.impl.PokemonClientImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.client.impl.PokemonSpecieClientImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonSpecieNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.facade.GetPokemonFromApiFacade;
import lombok.SneakyThrows;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetPokemonFromApiFacadeImpl implements GetPokemonFromApiFacade {

    private PokeApi pokeApi;

    public GetPokemonFromApiFacadeImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public Optional<PokemonNationalDexDto> getPokemon(Long id) {

        return getPokemonFromNationalDataBase(id);
    }

    @SneakyThrows
    private Optional<PokemonNationalDexDto> getPokemonFromNationalDataBase(Long id) {

        EvolutionChainClient evolutionRepository = new EvolutionChainClientImpl(this.pokeApi);
        PokemonSpecieClient specieRepository = new PokemonSpecieClientImpl(this.pokeApi);
        PokemonClient pokemonRepository = new PokemonClientImpl(this.pokeApi);

        Thread pokemon = new Thread(() ->
                pokemonRepository.getPokemonFromNationalDataBase(Integer.valueOf(id.toString()))
        );

        Thread specie = new Thread(() -> {
            specieRepository.getSpecieFromNationalDataBase(Integer.valueOf(id.toString()));
            evolutionRepository.getEvolutionChainFromNationalDataBase(Integer.valueOf(
                    specieRepository.getPokemonSpecie().map(PokemonSpecieNationalDexDto::getIdEvolutionChain)
                            .orElse(0L).toString()));
        });

        pokemon.start();
        specie.start();
        pokemon.join();
        specie.join();

        return getPokemon(evolutionRepository, specieRepository, pokemonRepository);
    }

    private Optional<PokemonNationalDexDto> getPokemon(EvolutionChainClient evolutionRepository,
                                                       PokemonSpecieClient specieRepository,
                                                       PokemonClient pokemonRepository) {

        Optional<PokemonNationalDexDto> pokemon = pokemonRepository.getPokemon();

        pokemon.ifPresent(i -> {
            i.setSpecie(specieRepository.getPokemonSpecie().orElseGet(() -> PokemonSpecieNationalDexDto.builder().build()));
            i.getSpecie().setEvolveTo(evolutionRepository.getEvolutions(i.getNumber()).orElse(null));
        });

        return pokemon;
    }
}

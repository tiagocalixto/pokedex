package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.adapter;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonSpecieApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.EvolutionChainApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.PokemonApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.PokemonSpecieApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.impl.EvolutionChainApiRepositoryImpl;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.impl.PokemonApiRepositoryImpl;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.impl.PokemonSpecieApiRepositoryImpl;
import br.com.tiagocalixto.pokedex.data_source_ports.FindGenericRepositoryPort;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import lombok.SneakyThrows;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PokemonNationalDbRepositoryAdapter implements FindGenericRepositoryPort<Pokemon> {

    private PokeApi pokeApi;

    @Autowired
    public PokemonNationalDbRepositoryAdapter(PokeApi pokeApi) {
        this.pokeApi = pokeApi;
    }


    @Override
    public Optional<Pokemon> findById(Long id) {

        //todo 1 - try to find in cache before access pokeApi

        // this.getPokemon(); todo 2 - you need to build converters
        return Optional.empty();
        //todo 3 - if find in api save on data on cache
    }

    @Override
    public boolean isExistsById(Long id) {
        return false;
        //   specieRepository.getSpecieFromNationalDataBase(Integer.valueOf(id.toString()));
        //  return specieRepository.getPokemonSpecie().isPresent();
    }

    @SneakyThrows
    private Optional<PokemonApi> getPokemonFromNationalDataBase(Long id) {

        EvolutionChainApiRepository evolutionRepository = new EvolutionChainApiRepositoryImpl(this.pokeApi);
        PokemonSpecieApiRepository specieRepository = new PokemonSpecieApiRepositoryImpl(this.pokeApi);
        PokemonApiRepository pokemonRepository = new PokemonApiRepositoryImpl(this.pokeApi);

        Thread pokemon = new Thread(() ->
                pokemonRepository.getPokemonFromNationalDataBase(Integer.valueOf(id.toString()))
        );

        Thread specie = new Thread(() -> {
            specieRepository.getSpecieFromNationalDataBase(Integer.valueOf(id.toString()));
            evolutionRepository.getEvolutionChainFromNationalDataBase(Integer.valueOf(
                    specieRepository.getPokemonSpecie().map(PokemonSpecieApi::getIdEvolutionChain)
                            .orElse(0L).toString()));
        });

        pokemon.start();
        specie.start();
        pokemon.join();
        specie.join();

        return getPokemon(evolutionRepository, specieRepository, pokemonRepository);
    }

    private Optional<PokemonApi> getPokemon(EvolutionChainApiRepository evolutionRepository,
                                            PokemonSpecieApiRepository specieRepository,
                                            PokemonApiRepository pokemonRepository) {

        Optional<PokemonApi> pokemon = pokemonRepository.getPokemon();

        pokemon.ifPresent(i -> {
            i.setSpecie(specieRepository.getPokemonSpecie().orElseGet(() -> PokemonSpecieApi.builder().build()));
            i.getSpecie().setEvolveTo(evolutionRepository.getEvolutions(i.getNumber()).orElse(null));
        });

        return pokemon;
    }
}

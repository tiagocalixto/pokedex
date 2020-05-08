package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.adapter;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonSpecieApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.EvolutionChainApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.PokemonApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.PokemonSpecieApiRepository;
import br.com.tiagocalixto.pokedex.data_source_ports.FindGenericRepositoryPort;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PokemonNationalDbRepositoryAdapter implements FindGenericRepositoryPort<Pokemon> {

    @Autowired
    EvolutionChainApiRepository evolutionRepository;

    @Autowired
    PokemonSpecieApiRepository specieRepository;

    @Autowired
    PokemonApiRepository pokemonRepository;


    @Override
    public Optional<Pokemon> findById(Long id) {

        //todo 1 - try to find in cache before access pokeApi

        this.executeThreadsToGetFromNationalDataBase(id);
        // this.getPokemon(); todo 2 - you need to build converters
        return Optional.empty();
        //todo 3 - if find in api save on data on cache
    }

    @Override
    public boolean isExistsById(Long id) {

        specieRepository.getSpecieFromNationalDataBase(Integer.valueOf(id.toString()));
        return specieRepository.getPokemonSpecie().isPresent();
    }

    @SneakyThrows
    private void executeThreadsToGetFromNationalDataBase(Long id) {

        Thread pokemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                pokemonRepository.getPokemonFromNationalDataBase(Integer.valueOf(id.toString()));
            }
        });

        Thread specieThread = new Thread(new Runnable() {
            @Override
            public void run() {
                specieRepository.getSpecieFromNationalDataBase(Integer.valueOf(id.toString()));
                evolutionRepository.getEvolutionChainFromNationalDataBase(Integer.valueOf(
                        specieRepository.getPokemonSpecie().map(PokemonSpecieApi::getIdEvolutionChain)
                                .orElse(0L).toString()));
            }
        });

        pokemonThread.start();
        specieThread.start();
        pokemonThread.join();
        specieThread.join();
    }

    private Optional<PokemonApi> getPokemon() {

        Optional<PokemonApi> pokemon = pokemonRepository.getPokemon();

        pokemon.ifPresent(i -> {
            i.setSpecie(specieRepository.getPokemonSpecie().orElseGet(() -> PokemonSpecieApi.builder().build()));
            i.getSpecie().setEvolveTo(evolutionRepository.getEvolutions(i.getNumber()).orElse(null));
        });

        return pokemon;
    }
}

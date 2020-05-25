package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.adapter;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.converter.ConverterNationalDb;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonNationalDb;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonSpecieNationalDb;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.cache.PokemonCache;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api.EvolutionChainApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api.PokemonApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api.PokemonSpecieApiRepository;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api.impl.EvolutionChainApiRepositoryImpl;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api.impl.PokemonApiRepositoryImpl;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api.impl.PokemonSpecieApiRepositoryImpl;
import br.com.tiagocalixto.pokedex.data_source_ports.FindByNumericFieldPort;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import lombok.SneakyThrows;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("pokemonNationalDb")
public class PokemonNationalDbRepositoryAdapter implements FindByNumericFieldPort<Pokemon> {

    private PokeApi pokeApi;
    private PokemonCache cache;
    private ConverterNationalDb<PokemonNationalDb, Pokemon> converter;

    @Autowired
    public PokemonNationalDbRepositoryAdapter(PokeApi pokeApi, PokemonCache cache,
                                              ConverterNationalDb<PokemonNationalDb, Pokemon> converter) {

        this.pokeApi = pokeApi;
        this.cache = cache;
        this.converter = converter;
    }


    @Override
    public Optional<Pokemon> findBy(Long id) {

        Optional<PokemonNationalDb> entity = cache.findById(id);

        if (entity.isEmpty()) {
            entity = getPokemonFromNationalDataBase(id);
            entity.ifPresent(cache::save);
        }

        return converter.convertToDomain(entity);
    }


    @SneakyThrows
    private Optional<PokemonNationalDb> getPokemonFromNationalDataBase(Long id) {

        EvolutionChainApiRepository evolutionRepository = new EvolutionChainApiRepositoryImpl(this.pokeApi);
        PokemonSpecieApiRepository specieRepository = new PokemonSpecieApiRepositoryImpl(this.pokeApi);
        PokemonApiRepository pokemonRepository = new PokemonApiRepositoryImpl(this.pokeApi);

        Thread pokemon = new Thread(() ->
                pokemonRepository.getPokemonFromNationalDataBase(Integer.valueOf(id.toString()))
        );

        Thread specie = new Thread(() -> {
            specieRepository.getSpecieFromNationalDataBase(Integer.valueOf(id.toString()));
            evolutionRepository.getEvolutionChainFromNationalDataBase(Integer.valueOf(
                    specieRepository.getPokemonSpecie().map(PokemonSpecieNationalDb::getIdEvolutionChain)
                            .orElse(0L).toString()));
        });

        pokemon.start();
        specie.start();
        pokemon.join();
        specie.join();

        return getPokemon(evolutionRepository, specieRepository, pokemonRepository);
    }

    private Optional<PokemonNationalDb> getPokemon(EvolutionChainApiRepository evolutionRepository,
                                                   PokemonSpecieApiRepository specieRepository,
                                                   PokemonApiRepository pokemonRepository) {

        Optional<PokemonNationalDb> pokemon = pokemonRepository.getPokemon();

        pokemon.ifPresent(i -> {
            i.setSpecie(specieRepository.getPokemonSpecie().orElseGet(() -> PokemonSpecieNationalDb.builder().build()));
            i.getSpecie().setEvolveTo(evolutionRepository.getEvolutions(i.getNumber()).orElse(null));
        });

        return pokemon;
    }
}

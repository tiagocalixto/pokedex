package br.com.tiagocalixto.pokedex.data_source.cache.adapter;

import br.com.tiagocalixto.pokedex.data_source.cache.converter.ConverterCache;
import br.com.tiagocalixto.pokedex.data_source.cache.entity.PokemonCache;
import br.com.tiagocalixto.pokedex.data_source.cache.repository.PokemonRepositoryCache;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.FindOneByIdRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("PokemonCache")
public class PokemonCacheAdapter implements FindOneByIdRepositoryPort<Pokemon>,
                                                  InsertRepositoryPort<Pokemon> {

    //<editor-fold: properties>
    private ConverterCache<PokemonCache, Pokemon> converter;
    private PokemonRepositoryCache cacheRepository;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonCacheAdapter(ConverterCache<PokemonCache, Pokemon> converter,
                               PokemonRepositoryCache cacheRepository) {
        this.converter = converter;
        this.cacheRepository = cacheRepository;
    }
    //</editor-fold>


    @Override
    public Optional<Pokemon> findById(Long id) {

        return converter.convertToDomain(cacheRepository.findById(id));
    }

    @Override
    public Pokemon insert(Pokemon pokemon) {

        return converter.convertToDomainNotOptional(cacheRepository.save(
                converter.convertToCacheNotOptional(pokemon)));
    }
}

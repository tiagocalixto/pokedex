package br.com.tiagocalixto.pokedex.data_source.cache.adapter;

import br.com.tiagocalixto.pokedex.data_source.cache.converter.ConverterCache;
import br.com.tiagocalixto.pokedex.data_source.cache.entity.PokemonNationalDexCache;
import br.com.tiagocalixto.pokedex.data_source.cache.repository.PokemonNationalDexRepositoryCache;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.FindOneByIdRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("NationalDexCache")
public class PokemonNationalDexCacheAdapter implements FindOneByIdRepositoryPort<PokemonNationalDexDto>,
                                                  InsertRepositoryPort<PokemonNationalDexDto> {

    //<editor-fold: properties>
    private ConverterCache<PokemonNationalDexCache, PokemonNationalDexDto> converter;
    private PokemonNationalDexRepositoryCache cacheRepository;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonNationalDexCacheAdapter(ConverterCache<PokemonNationalDexCache, PokemonNationalDexDto> converter,
                                          PokemonNationalDexRepositoryCache cacheRepository) {
        this.converter = converter;
        this.cacheRepository = cacheRepository;
    }
    //</editor-fold>


    @Override
    public Optional<PokemonNationalDexDto> findById(Long id) {

        return converter.convertToDomain(cacheRepository.findById(id));
    }

    @Override
    public PokemonNationalDexDto insert(PokemonNationalDexDto pokemon) {

        return converter.convertToDomainNotOptional(cacheRepository.save(
                converter.convertToCacheNotOptional(pokemon)));
    }
}

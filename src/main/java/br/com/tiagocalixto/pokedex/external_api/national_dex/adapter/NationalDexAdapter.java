package br.com.tiagocalixto.pokedex.external_api.national_dex.adapter;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.external_api.national_dex.converter.ConverterCache;
import br.com.tiagocalixto.pokedex.external_api.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.external_api.national_dex.facade.GetPokemonFromApiFacade;
import br.com.tiagocalixto.pokedex.external_api.through_cache.entity.PokemonCache;
import br.com.tiagocalixto.pokedex.external_api.through_cache.repository.PokemonRepositoryCache;
import br.com.tiagocalixto.pokedex.ports.external_api.FindOneByIdExternalApiPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("nationalDex")
public class NationalDexAdapter implements FindOneByIdExternalApiPort<Pokemon> {

    //<editor-fold: properties>
    private PokemonRepositoryCache cache;
    private GetPokemonFromApiFacade getFromNationalDex;
    private ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter;
    private ConverterCache<PokemonCache, PokemonNationalDexDto> cacheConverter;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public NationalDexAdapter(PokemonRepositoryCache cache, GetPokemonFromApiFacade getFromNationalDex,
                              ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter,
                              ConverterCache<PokemonCache, PokemonNationalDexDto> cacheConverter) {
        this.cache = cache;
        this.getFromNationalDex = getFromNationalDex;
        this.converter = converter;
        this.cacheConverter = cacheConverter;
    }
    //</editor-fold>


    @Override
    public Optional<Pokemon> findById(Long id) {

        Optional<PokemonNationalDexDto> entity = cacheConverter.convertToNationalDex(cache.findById(id));

        if (entity.isEmpty()) {
            entity = getFromNationalDex.getPokemon(id);
            entity.ifPresent(item -> cache.save(cacheConverter.convertToCacheNotOptional(item)));
        }

        return converter.convertToDomain(entity);
    }
}

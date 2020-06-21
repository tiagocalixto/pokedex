package br.com.tiagocalixto.pokedex.data_source.cache.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.cache.converter.ConverterCache;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.data_source.cache.entity.PokemonNationalDexCache;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("NationalDexCacheConverter")
public class ConverterCacheNationalDexImpl implements ConverterCache<PokemonNationalDexCache, PokemonNationalDexDto> {

    private Gson gson;

    @Autowired
    public ConverterCacheNationalDexImpl(Gson gson){
        this.gson = gson;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<PokemonNationalDexCache> convertToCache(Optional<PokemonNationalDexDto> dto) {

        if(dto.isEmpty())
            return Optional.empty();

        PokemonNationalDexCache cache = PokemonNationalDexCache.builder().build();

        dto.ifPresent(item -> {
            cache.setId(item.getId());
            cache.setBody(gson.toJson(item));
        });

        return Optional.of(cache);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<PokemonNationalDexDto> convertToDomain(Optional<PokemonNationalDexCache> cache) {

        if(cache.isEmpty())
            return Optional.empty();

        PokemonNationalDexCache pokemonNationalDexCache = cache.orElseGet(PokemonNationalDexCache::new);
        return Optional.of(gson.fromJson(pokemonNationalDexCache.getBody(), PokemonNationalDexDto.class));
    }
}

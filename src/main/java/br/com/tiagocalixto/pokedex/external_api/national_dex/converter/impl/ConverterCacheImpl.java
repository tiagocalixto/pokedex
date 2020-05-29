package br.com.tiagocalixto.pokedex.external_api.national_dex.converter.impl;

import br.com.tiagocalixto.pokedex.external_api.national_dex.converter.ConverterCache;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.external_api.through_cache.entity.PokemonCache;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterCacheImpl implements ConverterCache<PokemonCache, PokemonNationalDexDto> {

    private Gson gson;

    @Autowired
    public ConverterCacheImpl(Gson gson){
        this.gson = gson;
    }

    @Override
    public Optional<PokemonCache> convertToCache(Optional<PokemonNationalDexDto> dto) {

        if(dto.isEmpty())
            return Optional.empty();

        PokemonCache cache = PokemonCache.builder().build();

        dto.ifPresent(item -> {
            cache.setId(item.getId());
            cache.setBody(gson.toJson(item));
        });

        return Optional.of(cache);
    }

    @Override
    public Optional<PokemonNationalDexDto> convertToNationalDex(Optional<PokemonCache> cache) {

        if(cache.isEmpty())
            return Optional.empty();

        PokemonCache pokemonCache = cache.orElseGet(PokemonCache::new);
        return Optional.of(gson.fromJson(pokemonCache.getBody(), PokemonNationalDexDto.class));
    }
}

package br.com.tiagocalixto.pokedex.data_source.cache.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.cache.converter.ConverterCache;
import br.com.tiagocalixto.pokedex.data_source.cache.entity.PokemonCache;
import br.com.tiagocalixto.pokedex.data_source.cache.entity.PokemonNationalDexCache;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.pokemon.PokemonNationalDexDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("PokemonCacheConverter")
public class ConverterPokemonImpl implements ConverterCache<PokemonCache, Pokemon> {

    private Gson gson;


    @Autowired
    public ConverterPokemonImpl(Gson gson){
        this.gson = gson;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<PokemonCache> convertToCache(Optional<Pokemon> domain) {

        if(domain.isEmpty())
            return Optional.empty();

        PokemonCache cache = PokemonCache.builder().build();

        domain.ifPresent(item -> {
            cache.setId(item.getNumber());
            cache.setBody(gson.toJson(item));
        });

        return Optional.of(cache);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Pokemon> convertToDomain(Optional<PokemonCache> cache) {

        if(cache.isEmpty())
            return Optional.empty();

        PokemonCache pokemonCache = cache.orElseGet(PokemonCache::new);

        return Optional.of(gson.fromJson(pokemonCache.getBody(), Pokemon.class));
    }
}

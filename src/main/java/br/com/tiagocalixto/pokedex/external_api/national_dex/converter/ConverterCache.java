package br.com.tiagocalixto.pokedex.external_api.national_dex.converter;

import java.util.Optional;

public interface ConverterCache<C, D> {

    Optional<C> convertToCache(Optional<D> dto);

    Optional<D> convertToNationalDex(Optional<C> cache);

    default C convertToCacheNotOptional(D dto) {
        return convertToCache(Optional.ofNullable(dto)).orElse(null);
    }
}

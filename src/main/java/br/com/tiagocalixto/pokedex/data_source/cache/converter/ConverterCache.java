package br.com.tiagocalixto.pokedex.data_source.cache.converter;

import java.util.Optional;

public interface ConverterCache<C, D> {

    Optional<C> convertToCache(Optional<D> dto);
    Optional<D> convertToDomain(Optional<C> cache);

    default C convertToCacheNotOptional(D dto) {

        return convertToCache(Optional.ofNullable(dto)).orElse(null);
    }

    default D convertToDomainNotOptional(C domain) {

        return convertToDomain(Optional.ofNullable(domain)).orElse(null);
    }
}

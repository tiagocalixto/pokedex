package br.com.tiagocalixto.pokedex.controller.v1.converter;

import java.util.*;
import java.util.stream.Collectors;

public interface ConverterDto<T, D> {

    Optional<T> convertToDto(Optional<D> domain);

    Optional<D> convertToDomain(Optional<T> dto);

    @SuppressWarnings("Duplicates")
    default List<T> convertToDtoList(final Collection<D> domainList) {

        if (domainList == null || domainList.isEmpty())
            return Collections.emptyList();

        return Optional.ofNullable(domainList).orElse(Collections.emptyList())
                .stream().map(item -> this.convertToDto(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    default List<D> convertToDomainList(final Collection<T> dtoList) {

        if (dtoList == null || dtoList.isEmpty())
            return Collections.emptyList();

        return Optional.ofNullable(dtoList).orElse(Collections.emptyList())
                .stream().map(item -> this.convertToDomain(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    default T convertToDtoNotOptional(D domain) {

        return convertToDto(Optional.ofNullable(domain)).orElse(null);
    }

    default D convertToDomainNotOptional(T dto) {

        return convertToDomain(Optional.ofNullable(dto)).orElse(null);
    }
}

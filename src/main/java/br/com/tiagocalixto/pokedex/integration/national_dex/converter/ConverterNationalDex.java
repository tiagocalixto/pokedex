package br.com.tiagocalixto.pokedex.integration.national_dex.converter;

import java.util.*;
import java.util.stream.Collectors;

public interface ConverterNationalDex<T, D> {

    Optional<D> convertToDomain(Optional<T> dto);

    @SuppressWarnings("Duplicates")
    default List<D> convertToDomainList(final Collection<T> dtoList) {

        if (dtoList == null || dtoList.isEmpty())
            return Collections.emptyList();

        return Optional.ofNullable(dtoList).orElse(Collections.emptyList())
                .stream().map(item -> this.convertToDomain(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

}

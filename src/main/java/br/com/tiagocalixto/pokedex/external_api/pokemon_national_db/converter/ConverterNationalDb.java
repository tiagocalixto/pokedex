package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.converter;

import java.util.*;
import java.util.stream.Collectors;

public interface ConverterNationalDb<N, D> {

    Optional<D> convertToDomain(Optional<N> nationalDbEntity);

    @SuppressWarnings("Duplicates")
    default List<D> convertToDomainList(final Collection<N> nationalDbList) {

        if (nationalDbList == null || nationalDbList.isEmpty())
            return Collections.emptyList();

        return Optional.ofNullable(nationalDbList).orElse(Collections.emptyList())
                .stream().map(item -> this.convertToDomain(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}

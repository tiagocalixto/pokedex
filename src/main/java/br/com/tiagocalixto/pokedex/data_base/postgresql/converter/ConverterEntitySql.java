package br.com.tiagocalixto.pokedex.data_base.postgresql.converter;

import java.util.*;
import java.util.stream.Collectors;

public interface ConverterEntitySql<E, D> {

    Optional<E> convertToEntity(Optional<D> domain);

    Optional<D> convertToDomain(Optional<E> entity);

    @SuppressWarnings("Duplicates")
    default List<E> convertToEntityList(final Collection<D> domainList) {

        if (domainList.isEmpty())
            return Collections.emptyList();

        return Optional.ofNullable(domainList).orElse(Collections.emptyList())
                .stream().map(item -> this.convertToEntity(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    default List<D> convertToDomainList(final Collection<E> entityList) {

        if (entityList.isEmpty())
            return Collections.emptyList();

        return Optional.ofNullable(entityList).orElse(Collections.emptyList())
                .stream().map(item -> this.convertToDomain(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    default E convertToEntityNotOptional(D domain) {

        return convertToEntity(Optional.ofNullable(domain)).orElse(null);
    }

    default D convertToDomainNotOptional(E entity) {

        return convertToDomain(Optional.ofNullable(entity)).orElse(null);
    }
}

package br.com.tiagocalixto.pokedex.converter.entity.impl;

import br.com.tiagocalixto.pokedex.converter.entity.ConverterEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.domain.Type;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterTypeEntityImpl implements ConverterEntity<TypeEntity, Type> {

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<TypeEntity> convertToEntity(Optional<Type> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        TypeEntity typeEntity = TypeEntity.builder().build();

        domain.ifPresent(item -> {
            typeEntity.setId(item.getId());
            typeEntity.setDescription(item.getDescription());
        });

        return Optional.of(typeEntity);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Type> convertToDomain(Optional<TypeEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Type type = Type.builder().build();

        entity.ifPresent(item -> {
            type.setId(item.getId());
            type.setDescription(item.getDescription());
        });

        return Optional.of(type);
    }
}

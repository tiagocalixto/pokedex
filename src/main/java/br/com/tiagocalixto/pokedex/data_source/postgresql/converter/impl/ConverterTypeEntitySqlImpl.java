package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("typeConverterEntity")
public class ConverterTypeEntitySqlImpl implements ConverterEntitySql<TypeEntity, Type> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<TypeEntity> convertToEntity(Optional<Type> domain) {

        if (domain.isEmpty()) //TODO - CREATE EXTENSION unaccent; on postgres - put in script deploy
            return Optional.empty();

        TypeEntity typeEntity = TypeEntity.builder().build();

        domain.ifPresent(item -> {
            typeEntity.setId(0L);
            typeEntity.setDescription(item.getDescription().toString());
        });

        return Optional.of(typeEntity);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Type> convertToDomain(Optional<TypeEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Type type = Type.builder().build();

        entity.ifPresent(item ->
                type.setDescription(TypeEnum.valueOf(item.getDescription().trim().toUpperCase()))
        );

        return Optional.of(type);
    }
}

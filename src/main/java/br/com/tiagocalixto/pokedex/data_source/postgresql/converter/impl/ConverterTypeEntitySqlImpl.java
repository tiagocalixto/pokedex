package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.TypeRepository;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("typeConverterEntity")
public class ConverterTypeEntitySqlImpl implements ConverterEntitySql<TypeEntity, Type> {

    private TypeRepository repository;

    @Autowired
    public ConverterTypeEntitySqlImpl(TypeRepository repository) {
        this.repository = repository;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<TypeEntity> convertToEntity(Optional<Type> domain) {

        if (domain.isEmpty()) //TODO - CREATE EXTENSION unaccent; on postgres - put in script deploy
            return Optional.empty();

        Type type = domain.orElseGet(Type::new);

        TypeEntity typeEntity = repository
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(type.getDescription().toString())
                .orElse(TypeEntity.builder()
                        .id(0L)
                        .description(type.getDescription().toString())
                        .build()
                );

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
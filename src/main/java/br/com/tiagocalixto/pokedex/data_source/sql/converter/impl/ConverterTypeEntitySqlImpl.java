package br.com.tiagocalixto.pokedex.data_source.sql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.sql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.TypeRepository;
import br.com.tiagocalixto.pokedex.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterTypeEntitySqlImpl implements ConverterEntitySql<TypeEntity, Type> {

    private TypeRepository repository;

    @Autowired
    public ConverterTypeEntitySqlImpl(TypeRepository repository){
        this.repository = repository;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<TypeEntity> convertToEntity(Optional<Type> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        Type type = domain.orElseGet(Type::new);

        TypeEntity typeEntity = repository.findFirstByDescriptionIgnoreCaseAndIgnoreAccents(type.getDescription())
                .orElse(TypeEntity.builder()
                        .id(0L)
                        .description(type.getDescription())
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
                type.setDescription(item.getDescription())
        );

        return Optional.of(type);
    }
}

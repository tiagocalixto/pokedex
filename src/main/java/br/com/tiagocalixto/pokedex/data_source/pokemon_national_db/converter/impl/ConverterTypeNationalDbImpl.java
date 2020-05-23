package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.converter.ConverterNationalDb;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.TypeNationalDb;
import br.com.tiagocalixto.pokedex.domain.Type;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterTypeNationalDbImpl implements ConverterNationalDb<TypeNationalDb, Type> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Type> convertToDomain(Optional<TypeNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        Type type = Type.builder().build();

        nationalDbEntity.ifPresent(item ->
            type.setDescription(item.getDescription())
        );

        return Optional.of(type);
    }
}

package br.com.tiagocalixto.pokedex.external_api.national_dex.converter.impl;

import br.com.tiagocalixto.pokedex.external_api.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterTypeNationalDexImpl implements ConverterNationalDex<TypeNationalDexDto, Type> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Type> convertToDomain(Optional<TypeNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Type type = Type.builder().build();

        dto.ifPresent(item ->
            type.setDescription(TypeEnum.valueOf(item.getDescription().toUpperCase().trim()))
        );

        return Optional.of(type);
    }
}

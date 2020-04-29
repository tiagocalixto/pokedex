package br.com.tiagocalixto.pokedex.controller.converter.impl;

import br.com.tiagocalixto.pokedex.controller.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.converter.ConverterDto;
import br.com.tiagocalixto.pokedex.domain.Type;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterTypeDtoImpl implements ConverterDto<TypeDto, Type> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<TypeDto> convertToDto(Optional<Type> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        TypeDto typeDto = TypeDto.builder().build();

        domain.ifPresent(item -> {
            typeDto.setDescription(item.getDescription());
        });

        return Optional.of(typeDto);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Type> convertToDomain(Optional<TypeDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Type type = Type.builder().build();

        dto.ifPresent(item -> {
            type.setDescription(item.getDescription());
        });

        return Optional.of(type);
    }
}

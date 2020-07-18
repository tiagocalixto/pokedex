package br.com.tiagocalixto.pokedex.controller.v1.converter.impl;

import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.v1.converter.ConverterDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.TypeDtoEnum;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("Duplicates")
@Component
public class ConverterTypeDtoImpl implements ConverterDto<TypeDto, Type> {


    @Override
    public Optional<TypeDto> convertToDto(Optional<Type> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        TypeDto typeDto = TypeDto.builder().build();

        domain.ifPresent(item ->
            typeDto.setDescription(TypeDtoEnum.valueOf(item.getDescription().toString()))
        );

        return Optional.of(typeDto);
    }

    @Override
    public Optional<Type> convertToDomain(Optional<TypeDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Type type = Type.builder().build();

        dto.ifPresent(item ->
            type.setDescription(TypeEnum.valueOf(item.getDescription().toString()))
        );

        return Optional.of(type);
    }
}

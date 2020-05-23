package br.com.tiagocalixto.pokedex.controller.converter.impl;

import br.com.tiagocalixto.pokedex.controller.dto.MoveDto;
import br.com.tiagocalixto.pokedex.controller.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.converter.ConverterDto;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterMoveDtoImpl implements ConverterDto<MoveDto, Move> {

    @Autowired
    ConverterDto<TypeDto, Type> convertType;

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<MoveDto> convertToDto(Optional<Move> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        MoveDto moveDto = MoveDto.builder().build();

        domain.ifPresent(item -> {
            moveDto.setDescription(item.getDescription());
            moveDto.setPower(item.getPower());
            moveDto.setPp(item.getPp());
            moveDto.setAbout(item.getAbout());
            moveDto.setAccuracy(item.getAccuracy());
            convertType.convertToDto(Optional.ofNullable(item.getType())).ifPresent(moveDto::setType);
        });

        return Optional.of(moveDto);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Move> convertToDomain(Optional<MoveDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Move move = Move.builder().build();

        dto.ifPresent(item -> {
            move.setDescription(item.getDescription());
            move.setPower(item.getPower());
            move.setPp(item.getPp());
            move.setAbout(item.getAbout());
            move.setAccuracy(item.getAccuracy());
            convertType.convertToDomain(Optional.ofNullable(item.getType())).ifPresent(move::setType);
        });

        return Optional.of(move);
    }
}
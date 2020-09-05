package br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl;

import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterMoveNationalDexImpl implements ConverterNationalDex<MoveNationalDexDto, Move> {

    private ConverterNationalDex<TypeNationalDexDto, Type> converterType;

    public ConverterMoveNationalDexImpl(ConverterNationalDex<TypeNationalDexDto, Type> converterType) {
        this.converterType = converterType;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Move> convertToDomain(Optional<MoveNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Move move = Move.builder().build();

        dto.ifPresent(item ->{
            move.setAbout(item.getAbout());
            move.setDescription(item.getDescription());
            move.setAccuracy(item.getAccuracy());
            move.setPower(item.getPower());
            move.setPp(item.getPp());
            converterType.convertToDomain(Optional.of(item.getType())).ifPresent(move::setType);
        });

        return Optional.of(move);
    }
}

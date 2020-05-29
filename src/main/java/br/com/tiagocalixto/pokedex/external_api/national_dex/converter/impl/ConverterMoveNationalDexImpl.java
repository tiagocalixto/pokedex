package br.com.tiagocalixto.pokedex.external_api.national_dex.converter.impl;

import br.com.tiagocalixto.pokedex.external_api.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.domain.Move;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterMoveNationalDexImpl implements ConverterNationalDex<MoveNationalDexDto, Move> {


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
            move.setType(item.getType());
        });

        return Optional.of(move);
    }
}

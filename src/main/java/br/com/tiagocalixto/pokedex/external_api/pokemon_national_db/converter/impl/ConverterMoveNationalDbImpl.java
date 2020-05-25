package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.converter.impl;

import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.converter.ConverterNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.MoveNationalDb;
import br.com.tiagocalixto.pokedex.domain.Move;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterMoveNationalDbImpl implements ConverterNationalDb<MoveNationalDb, Move> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Move> convertToDomain(Optional<MoveNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        Move move = Move.builder().build();

        nationalDbEntity.ifPresent(item ->{
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

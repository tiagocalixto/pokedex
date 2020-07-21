package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.MoveEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("Duplicates")
@Component
public class ConverterMoveEntityImpl implements ConverterEntitySql<MoveEntity, Move> {

    private ConverterEntitySql<TypeEntity, Type> convertType;

    public ConverterMoveEntityImpl(ConverterEntitySql<TypeEntity, Type> convertType) {
        this.convertType = convertType;
    }


    @Override
    public Optional<MoveEntity> convertToEntity(Optional<Move> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        MoveEntity moveEntity = MoveEntity.builder().build();

        domain.ifPresent(item -> {
            moveEntity.setId(0L);
            moveEntity.setDescription(Util
                    .removeUndesirableChars(item.getDescription()).toUpperCase());
            moveEntity.setPower(item.getPower());
            moveEntity.setPp(item.getPp());
            moveEntity.setAbout(item.getAbout());
            moveEntity.setAccuracy(item.getAccuracy());
            convertType.convertToEntity(Optional.ofNullable(item.getType())).ifPresent(moveEntity::setType);
        });

        return Optional.of(moveEntity);
    }

    @Override
    public Optional<Move> convertToDomain(Optional<MoveEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Move move = Move.builder().build();

        entity.ifPresent(item -> {
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

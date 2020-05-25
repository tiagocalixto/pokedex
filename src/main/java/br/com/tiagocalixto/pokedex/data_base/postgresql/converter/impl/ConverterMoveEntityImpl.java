package br.com.tiagocalixto.pokedex.data_base.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_base.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.MoveEntity;
import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.data_base.postgresql.repository.MoveRepository;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("moveConverterEntity")
public class ConverterMoveEntityImpl implements ConverterEntitySql<MoveEntity, Move> {

    private ConverterEntitySql<TypeEntity, Type> convertType;
    private MoveRepository repository;

    @Autowired
    public ConverterMoveEntityImpl (MoveRepository repository, ConverterEntitySql<TypeEntity, Type> convertType){
        this.convertType = convertType;
        this.repository = repository;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<MoveEntity> convertToEntity(Optional<Move> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        Move move = domain.orElseGet(Move::new);

        MoveEntity moveEntity = repository
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(move.getDescription())
                .orElse(MoveEntity.builder()
                        .id(0L)
                        .description(move.getDescription())
                        .build()
                );

        moveEntity.setPower(move.getPower());
        moveEntity.setPp(move.getPp());
        moveEntity.setAbout(move.getAbout());
        moveEntity.setAccuracy(move.getAccuracy());
        convertType.convertToEntity(Optional.ofNullable(move.getType())).ifPresent(moveEntity::setType);

        return Optional.of(moveEntity);
    }

    @SuppressWarnings("Duplicates")
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

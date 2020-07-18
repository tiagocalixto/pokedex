package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionStoneEntity;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionStoneEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("Duplicates")
@Component
public class ConverterEvolutionStoneSqlImpl implements ConverterEntitySql<EvolutionStoneEntity, EvolutionStoneEnum> {



    @Override
    public Optional<EvolutionStoneEntity> convertToEntity(Optional<EvolutionStoneEnum> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        EvolutionStoneEntity entity = EvolutionStoneEntity.builder().build();

        domain.ifPresent(item -> {
            entity.setId(0L);
            entity.setDescription(item.toString().toUpperCase());
        });

        return Optional.of(entity);
    }

    @Override
    public Optional<EvolutionStoneEnum> convertToDomain(Optional<EvolutionStoneEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        EvolutionStoneEntity stoneEntity = EvolutionStoneEntity.builder().build();
        entity.ifPresent(item -> stoneEntity.setDescription(item.getDescription()));
        EvolutionStoneEnum stoneEnum = EvolutionStoneEnum.valueOf(stoneEntity.getDescription());

        return Optional.of(stoneEnum);
    }
}

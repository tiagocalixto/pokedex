package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionStoneEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.EvolutionStoneRepository;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionStoneEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("ConverterEvolutionStone")
public class ConverterEvolutionStoneSqlImpl implements ConverterEntitySql<EvolutionStoneEntity, EvolutionStoneEnum> {

    private EvolutionStoneRepository repository;

    @Autowired
    public ConverterEvolutionStoneSqlImpl(EvolutionStoneRepository repository) {

        this.repository = repository;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<EvolutionStoneEntity> convertToEntity(Optional<EvolutionStoneEnum> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        EvolutionStoneEnum evolutionStoneEnum = domain.orElse(EvolutionStoneEnum.NOT_INFORMED);

        EvolutionStoneEntity entity = repository
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(evolutionStoneEnum.toString())
                .orElse(repository.save(
                        EvolutionStoneEntity.builder()
                                .id(0L)
                                .description(evolutionStoneEnum.toString())
                                .build()));

        return Optional.of(entity);
    }

    @SuppressWarnings("Duplicates")
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

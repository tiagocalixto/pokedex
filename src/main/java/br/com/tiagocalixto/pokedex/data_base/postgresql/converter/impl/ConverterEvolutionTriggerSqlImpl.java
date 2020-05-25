package br.com.tiagocalixto.pokedex.data_base.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_base.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.EvolutionTriggerEntity;
import br.com.tiagocalixto.pokedex.data_base.postgresql.repository.EvolutionTriggerRepository;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("ConverterEvolutionTrigger")
public class ConverterEvolutionTriggerSqlImpl implements ConverterEntitySql<EvolutionTriggerEntity, EvolutionTriggerEnum> {

    private EvolutionTriggerRepository repository;

    @Autowired
    public ConverterEvolutionTriggerSqlImpl(EvolutionTriggerRepository repository) {

        this.repository = repository;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<EvolutionTriggerEntity> convertToEntity(Optional<EvolutionTriggerEnum> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        EvolutionTriggerEnum evolutionTriggerEnum = domain.orElse(EvolutionTriggerEnum.LEVEL_UP);

        EvolutionTriggerEntity entity = repository
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(evolutionTriggerEnum.toString())
                .orElse(EvolutionTriggerEntity.builder()
                        .id(0L)
                        .description(evolutionTriggerEnum.toString())
                        .build());

        return Optional.of(entity);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<EvolutionTriggerEnum> convertToDomain(Optional<EvolutionTriggerEntity> entity) {

        if(entity.isEmpty())
            return Optional.empty();

        EvolutionTriggerEntity triggerEntity = EvolutionTriggerEntity.builder().build();
        entity.ifPresent(item -> triggerEntity.setDescription(item.getDescription()));
        EvolutionTriggerEnum triggerEnum = EvolutionTriggerEnum.valueOf(triggerEntity.getDescription());

        return Optional.of(triggerEnum);
    }
}

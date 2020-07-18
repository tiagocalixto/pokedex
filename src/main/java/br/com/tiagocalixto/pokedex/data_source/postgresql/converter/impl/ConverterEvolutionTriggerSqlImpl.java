package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionTriggerEntity;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("Duplicates")
@Component
public class ConverterEvolutionTriggerSqlImpl implements ConverterEntitySql<EvolutionTriggerEntity, EvolutionTriggerEnum> {


    @Override
    public Optional<EvolutionTriggerEntity> convertToEntity(Optional<EvolutionTriggerEnum> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        EvolutionTriggerEntity entity = EvolutionTriggerEntity.builder().build();

        domain.ifPresentOrElse(item -> {
                    entity.setId(0L);
                    entity.setDescription(item.toString().toUpperCase());
                }
                , () -> {
                    entity.setId(0L);
                    entity.setDescription(EvolutionTriggerEnum.LEVEL_UP.toString().toUpperCase());
                }
        );

        return Optional.of(entity);
    }

    @Override
    public Optional<EvolutionTriggerEnum> convertToDomain(Optional<EvolutionTriggerEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        EvolutionTriggerEntity triggerEntity = EvolutionTriggerEntity.builder().build();
        entity.ifPresent(item -> triggerEntity.setDescription(item.getDescription()));
        EvolutionTriggerEnum triggerEnum = EvolutionTriggerEnum.valueOf(triggerEntity.getDescription());

        return Optional.of(triggerEnum);
    }
}

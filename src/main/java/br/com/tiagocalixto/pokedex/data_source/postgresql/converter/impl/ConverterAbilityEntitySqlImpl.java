package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.AbilityEntity;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("Duplicates")
@Component
public class ConverterAbilityEntitySqlImpl implements ConverterEntitySql<AbilityEntity, Ability> {


    @Override
    public Optional<AbilityEntity> convertToEntity(Optional<Ability> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        AbilityEntity abilityEntity = AbilityEntity.builder().build();

        domain.ifPresent(item -> {
            abilityEntity.setId(0L);
            abilityEntity.setDescription(Util
                    .removeUndesirableChars(item.getDescription()).toUpperCase());
        });

        return Optional.of(abilityEntity);
    }

    @Override
    public Optional<Ability> convertToDomain(Optional<AbilityEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Ability ability = Ability.builder().build();

        entity.ifPresent(item ->
                ability.setDescription(item.getDescription())
        );

        return Optional.of(ability);
    }
}

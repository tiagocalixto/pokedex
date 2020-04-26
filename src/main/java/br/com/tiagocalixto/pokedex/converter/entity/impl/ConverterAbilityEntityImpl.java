package br.com.tiagocalixto.pokedex.converter.entity.impl;


import br.com.tiagocalixto.pokedex.converter.entity.ConverterEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.AbilityEntity;
import br.com.tiagocalixto.pokedex.domain.Ability;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterAbilityEntityImpl implements ConverterEntity<AbilityEntity, Ability> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<AbilityEntity> convertToEntity(Optional<Ability> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        AbilityEntity abilityEntity = AbilityEntity.builder().build();

        domain.ifPresent(item -> {
            abilityEntity.setId(item.getId());
            abilityEntity.setDescription(item.getDescription());
        });

        return Optional.of(abilityEntity);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Ability> convertToDomain(Optional<AbilityEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Ability ability = Ability.builder().build();

        entity.ifPresent(item -> {
            ability.setId(item.getId());
            ability.setDescription(item.getDescription());
        });

        return Optional.of(ability);
    }
}

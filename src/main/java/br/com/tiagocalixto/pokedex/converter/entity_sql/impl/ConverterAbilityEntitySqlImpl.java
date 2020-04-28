package br.com.tiagocalixto.pokedex.converter.entity_sql.impl;


import br.com.tiagocalixto.pokedex.converter.entity_sql.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.AbilityEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.AbilityRepository;
import br.com.tiagocalixto.pokedex.domain.Ability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterAbilityEntitySqlImpl implements ConverterEntitySql<AbilityEntity, Ability> {

    @Autowired
    AbilityRepository repository;


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<AbilityEntity> convertToEntity(Optional<Ability> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        Ability ability = domain.orElseGet(Ability::new);

        AbilityEntity abilityEntity = repository
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(ability.getDescription()).orElse(
                        AbilityEntity.builder()
                                .id(0L)
                                .description(ability.getDescription())
                                .build());

        return Optional.of(abilityEntity);
    }

    @SuppressWarnings("Duplicates")
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

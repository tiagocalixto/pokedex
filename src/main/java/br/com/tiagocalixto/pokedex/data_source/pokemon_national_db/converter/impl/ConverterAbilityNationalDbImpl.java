package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.converter.ConverterNationalDb;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.AbilityNationalDb;
import br.com.tiagocalixto.pokedex.domain.Ability;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterAbilityNationalDbImpl implements ConverterNationalDb<AbilityNationalDb, Ability> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Ability> convertToDomain(Optional<AbilityNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        Ability ability = Ability.builder().build();

        nationalDbEntity.ifPresent(item -> {
            ability.setDescription(item.getDescription());
            ability.setAbout(item.getAbout());
        });

        return Optional.of(ability);
    }
}
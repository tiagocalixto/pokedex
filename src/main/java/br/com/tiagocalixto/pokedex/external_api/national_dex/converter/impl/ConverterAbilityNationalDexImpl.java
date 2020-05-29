package br.com.tiagocalixto.pokedex.external_api.national_dex.converter.impl;

import br.com.tiagocalixto.pokedex.external_api.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.domain.Ability;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterAbilityNationalDexImpl implements ConverterNationalDex<AbilityNationalDexDto, Ability> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Ability> convertToDomain(Optional<AbilityNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Ability ability = Ability.builder().build();

        dto.ifPresent(item ->
            ability.setDescription(item.getDescription())
        );

        return Optional.of(ability);
    }
}

package br.com.tiagocalixto.pokedex.converter.dto.impl;

import br.com.tiagocalixto.pokedex.controller.dto.AbilityDto;
import br.com.tiagocalixto.pokedex.converter.dto.ConverterDto;
import br.com.tiagocalixto.pokedex.domain.Ability;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConverterAbilityDtoImpl implements ConverterDto<AbilityDto, Ability> {


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<AbilityDto> convertToDto(Optional<Ability> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        AbilityDto abilityDto = AbilityDto.builder().build();

        domain.ifPresent(item -> {
            abilityDto.setDescription(item.getDescription());
        });

        return Optional.of(abilityDto);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Ability> convertToDomain(Optional<AbilityDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Ability ability = Ability.builder().build();

        dto.ifPresent(item -> {
            ability.setDescription(item.getDescription());
        });

        return Optional.of(ability);
    }
}

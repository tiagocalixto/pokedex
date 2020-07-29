package br.com.tiagocalixto.pokedex.controller.converter;

import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterAbilityDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.dto.AbilityDto;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class ConverterAbilityDtoTest {

    @InjectMocks
    private ConverterAbilityDtoImpl converter;


    @Test
    @SneakyThrows
    public void givenAbilityOptional_whenConvertToDto_thenReturnAbilityDtoOptional() {

        AbilityDto abilityDto = MocksDto.createAbility();
        Ability ability = MocksDomain.createAbility();

        Optional<AbilityDto> result = converter.convertToDto(Optional.of(ability));

        result.ifPresent(item ->
                assertThat(item).isEqualTo(abilityDto)
        );
    }

    @Test
    @SneakyThrows
    public void givenAbilityOptionalEmpty_whenConvertToDto_thenReturnOptionalEmpty() {

        Optional<AbilityDto> result = converter.convertToDto(Optional.empty());

        assertThat(Optional.empty()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    public void givenAbilityDtoOptional_whenConvertToDomain_thenReturnAbilityOptional() {

        AbilityDto abilityDto = MocksDto.createAbility();
        Ability ability = MocksDomain.createAbility();

        Optional<Ability> result = converter.convertToDomain(Optional.of(abilityDto));

        result.ifPresent(item ->
                assertThat(item).isEqualTo(ability)
        );
    }

    @Test
    @SneakyThrows
    public void givenAbilityDtoOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Ability> result = converter.convertToDomain(Optional.empty());

        assertThat(Optional.empty()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    public void givenAbility_whenConvertToDomainNotOptional_thenReturnAbilityDto() {

        AbilityDto abilityDto = MocksDto.createAbility();
        Ability ability = MocksDomain.createAbility();

        AbilityDto result = converter.convertToDtoNotOptional(ability);

        assertThat(result).isEqualTo(abilityDto);
    }

    @Test
    @SneakyThrows
    public void givenAbilityDto_whenConvertToDomainNotOptional_thenReturnAbility() {

        AbilityDto abilityDto = MocksDto.createAbility();
        Ability ability = MocksDomain.createAbility();

        Ability result = converter.convertToDomainNotOptional(abilityDto);

        assertThat(result).isEqualTo(ability);
    }

    @Test
    @SneakyThrows
    public void givenAbilityList_whenConvertToDtoList_thenReturnAbilityDtoList() {

        List<AbilityDto> abilityDto = List.of(MocksDto.createAbility());
        List<Ability> ability = List.of(MocksDomain.createAbility());

        List<AbilityDto> result = converter.convertToDtoList(ability);

        assertThat(result).isEqualTo(abilityDto);
    }

    @Test
    @SneakyThrows
    public void givenAbilityListEmpty_whenConvertToDtoList_thenReturnEmptyList() {

        List<AbilityDto> result = converter.convertToDtoList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    public void givenAbilityDtoList_whenConvertToDomainList_thenReturnAbilityList() {

        List<AbilityDto> abilityDto = List.of(MocksDto.createAbility());
        List<Ability> ability = List.of(MocksDomain.createAbility());

        List<Ability> result = converter.convertToDomainList(abilityDto);

        assertThat(result).isEqualTo(ability);
    }

    @Test
    @SneakyThrows
    public void givenAbilityDtoListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<Ability> result = converter.convertToDomainList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

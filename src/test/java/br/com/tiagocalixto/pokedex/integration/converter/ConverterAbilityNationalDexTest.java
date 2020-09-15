package br.com.tiagocalixto.pokedex.integration.converter;

import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl.ConverterAbilityNationalDexImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl.ConverterTypeNationalDexImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.mock.MocksNationalDexDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
class ConverterAbilityNationalDexTest {

    @InjectMocks
    private ConverterAbilityNationalDexImpl converter;


    @Test
    @SneakyThrows
    void givenAbilityOptional_whenConvertToDomain_thenReturnAbilityOptional() {

        AbilityNationalDexDto dto = MocksNationalDexDto.createAbility();

        Optional<Ability> result = converter.convertToDomain(Optional.of(dto));

        assertTrue(result.isPresent());

        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(dto.getDescription())
        );
    }

    @Test
    @SneakyThrows
    void givenAbilityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Ability> result = converter.convertToDomain(Optional.empty());

        assertFalse(result.isPresent());
    }

    @Test
    @SneakyThrows
    void givenAbilityList_whenConvertToDomainList_thenReturnAbilityList() {

        AbilityNationalDexDto dto = MocksNationalDexDto.createAbility();

        List<Ability> result = converter.convertToDomainList(List.of(dto));

        assertFalse(result.isEmpty());

        assertThat(result.get(0).getDescription())
                .isEqualTo(dto.getDescription());
    }

    @Test
    @SneakyThrows
    void givenEmptyList_whenConvertToDomainList_thenReturnEmptyList() {

        List<Ability> result = converter.convertToDomainList(Collections.emptyList());

        assertTrue(result.isEmpty());
    }
}
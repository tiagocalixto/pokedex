package br.com.tiagocalixto.pokedex.controller.converter;

import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterTypeDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.dto.AbilityDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Type;
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
class ConverterTypeDtoTest {

    @InjectMocks
    private ConverterTypeDtoImpl converter;


    @Test
    @SneakyThrows
    void givenTypeOptional_whenConvertToDto_thenReturnTypeDtoOptional() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        Optional<TypeDto> result = converter.convertToDto(Optional.of(type));

        result.ifPresent(item ->
                assertThat(item).isEqualTo(typeDto)
        );
    }

    @Test
    @SneakyThrows
    void givenTypeOptionalEmpty_whenConvertToDto_thenReturnOptionalEmpty() {

        Optional<TypeDto> result = converter.convertToDto(Optional.empty());

        assertThat(Optional.empty()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenTypeDtoOptional_whenConvertToDomain_thenReturnTypeOptional() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        Optional<Type> result = converter.convertToDomain(Optional.of(typeDto));

        result.ifPresent(item ->
                assertThat(item).isEqualTo(type)
        );
    }

    @Test
    @SneakyThrows
    void givenTypeDtoOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Type> result = converter.convertToDomain(Optional.empty());

        assertThat(Optional.empty()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenType_whenConvertToDomainNotOptional_thenReturnTypeDto() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        TypeDto result = converter.convertToDtoNotOptional(type);

        assertThat(result).isEqualTo(typeDto);
    }

    @Test
    @SneakyThrows
    void givenTypeDto_whenConvertToDomainNotOptional_thenReturnType() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        Type result = converter.convertToDomainNotOptional(typeDto);

        assertThat(result).isEqualTo(type);
    }

    @Test
    @SneakyThrows
    void givenTypeList_whenConvertToDomainList_thenReturnTypeDtoList() {

        List<TypeDto> typeDto = List.of(MocksDto.createType());
        List<Type> type = List.of(MocksDomain.createType());

        List<TypeDto> result = converter.convertToDtoList(type);

        assertThat(result).isEqualTo(typeDto);
    }

    @Test
    @SneakyThrows
    void givenTypeListEmpty_whenConvertToDtoList_thenReturnEmptyList() {

        List<TypeDto> result = converter.convertToDtoList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenTypeDtoList_whenConvertToDomainList_thenReturnTypeList() {

        List<TypeDto> typeDto = List.of(MocksDto.createType());
        List<Type> type = List.of(MocksDomain.createType());

        List<Type> result = converter.convertToDomainList(typeDto);

        assertThat(result).isEqualTo(type);
    }

    @Test
    @SneakyThrows
    void givenTypeDtoListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<Type> result = converter.convertToDomainList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

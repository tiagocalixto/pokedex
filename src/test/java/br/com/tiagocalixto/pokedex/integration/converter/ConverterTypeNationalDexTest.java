package br.com.tiagocalixto.pokedex.integration.converter;

import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl.ConverterTypeNationalDexImpl;
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
class ConverterTypeNationalDexTest {

    @InjectMocks
    private ConverterTypeNationalDexImpl converter;


    @Test
    @SneakyThrows
    void givenTypeOptional_whenConvertToDomain_thenReturnTypeOptional() {

        TypeNationalDexDto dto = MocksNationalDexDto.createType();

        Optional<Type> result = converter.convertToDomain(Optional.of(dto));

        assertTrue(result.isPresent());

        result.ifPresent(item ->
                assertThat(item.getDescription().toString()).isEqualTo(dto.getDescription())
        );
    }

    @Test
    @SneakyThrows
    void givenTypeOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Type> result = converter.convertToDomain(Optional.empty());

        assertFalse(result.isPresent());
    }

    @Test
    @SneakyThrows
    void givenTypeList_whenConvertToDomainList_thenReturnTypeList() {

        TypeNationalDexDto dto = MocksNationalDexDto.createType();

        List<Type> result = converter.convertToDomainList(List.of(dto));

        assertFalse(result.isEmpty());

        assertThat(result.get(0).getDescription().toString())
                .isEqualTo(dto.getDescription());
    }

    @Test
    @SneakyThrows
    void givenEmptyList_whenConvertToDomainList_thenReturnEmptyList() {

        List<Type> result = converter.convertToDomainList(Collections.emptyList());

        assertTrue(result.isEmpty());
    }
}
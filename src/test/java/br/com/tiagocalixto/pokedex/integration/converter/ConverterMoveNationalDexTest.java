package br.com.tiagocalixto.pokedex.integration.converter;

import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl.ConverterMoveNationalDexImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl.ConverterTypeNationalDexImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksNationalDexDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ConverterMoveNationalDexTest {

    @InjectMocks
    private ConverterMoveNationalDexImpl converter;

    @Mock
    private ConverterTypeNationalDexImpl converterType;


    @Test
    @SneakyThrows
    void givenMoveOptional_whenConvertToDomain_thenReturnMoveOptional() {

        MoveNationalDexDto dto = MocksNationalDexDto.createMove();
        TypeNationalDexDto typeDto = MocksNationalDexDto.createType();
        Type type = MocksDomain.createType();

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        Optional<Move> result = converter.convertToDomain(Optional.of(dto));

        assertTrue(result.isPresent());

        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(dto.getDescription())
        );
    }

    @Test
    @SneakyThrows
    void givenMoveOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Move> result = converter.convertToDomain(Optional.empty());

        assertFalse(result.isPresent());
    }

    @Test
    @SneakyThrows
    void givenMoveList_whenConvertToDomainList_thenReturnMoveList() {

        MoveNationalDexDto dto = MocksNationalDexDto.createMove();
        TypeNationalDexDto typeDto = MocksNationalDexDto.createType();
        Type type = MocksDomain.createType();

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        List<Move> result = converter.convertToDomainList(List.of(dto));

        assertFalse(result.isEmpty());

        assertThat(result.get(0).getDescription())
                .isEqualTo(dto.getDescription());
    }

    @Test
    @SneakyThrows
    void givenListEmpty_whenConvertToDomainList_thenReturnListEmpty() {

        List<Move> result = converter.convertToDomainList(Collections.emptyList());

        assertTrue(result.isEmpty());
    }
}
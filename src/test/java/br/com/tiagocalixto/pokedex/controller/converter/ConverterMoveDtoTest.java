package br.com.tiagocalixto.pokedex.controller.converter;

import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterMoveDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterTypeDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.dto.MoveDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.TypeDto;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ConverterMoveDtoTest {

    @InjectMocks
    private ConverterMoveDtoImpl converter;

    @Mock
    private ConverterTypeDtoImpl converterType;


    @Test
    @SneakyThrows
    public void givenMoveOptional_whenConvertToDto_thenReturnMoveDtoOptional() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();
        MoveDto moveDto = MocksDto.createMove();
        Move move = MocksDomain.createMove();

        when(converterType.convertToDto(Optional.of(type))).thenReturn(Optional.of(typeDto));

        Optional<MoveDto> result = converter.convertToDto(Optional.of(move));

        result.ifPresent(item ->
                assertThat(item).isEqualTo(moveDto)
        );

        verify(converterType, times(1)).convertToDto(Optional.of(type));
    }

    @Test
    @SneakyThrows
    public void givenMoveOptionalEmpty_whenConvertToDto_thenReturnOptionalEmpty() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        when(converterType.convertToDto(Optional.of(type))).thenReturn(Optional.of(typeDto));

        Optional<MoveDto> result = converter.convertToDto(Optional.empty());

        assertThat(Optional.empty()).isEqualTo(result);

        verify(converterType, times(0)).convertToDto(Optional.of(type));
    }

    @Test
    @SneakyThrows
    public void givenMoveDtoOptional_whenConvertToDomain_thenReturnMoveOptional() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();
        MoveDto moveDto = MocksDto.createMove();
        Move move = MocksDomain.createMove();

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        Optional<Move> result = converter.convertToDomain(Optional.of(moveDto));

        result.ifPresent(item ->
                assertThat(item).isEqualTo(move)
        );

        verify(converterType, times(1)).convertToDomain(Optional.of(typeDto));
    }

    @Test
    @SneakyThrows
    public void givenMoveDtoOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        Optional<Move> result = converter.convertToDomain(Optional.empty());

        assertThat(Optional.empty()).isEqualTo(result);

        verify(converterType, times(0)).convertToDomain(Optional.of(typeDto));
    }

    @Test
    @SneakyThrows
    public void givenMove_whenConvertToDtoNotOptional_thenReturnMoveDto() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();
        MoveDto moveDto = MocksDto.createMove();
        Move move = MocksDomain.createMove();

        when(converterType.convertToDto(Optional.of(type))).thenReturn(Optional.of(typeDto));

        MoveDto result = converter.convertToDtoNotOptional(move);

        assertThat(result).isEqualTo(moveDto);

        verify(converterType, times(1)).convertToDto(Optional.of(type));
    }

    @Test
    @SneakyThrows
    public void givenMoveDto_whenConvertToDomainNotOptional_thenReturnMove() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();
        MoveDto moveDto = MocksDto.createMove();
        Move move = MocksDomain.createMove();

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        Move result = converter.convertToDomainNotOptional(moveDto);

        assertThat(result).isEqualTo(move);

        verify(converterType, times(1)).convertToDomain(Optional.of(typeDto));
    }

    @Test
    @SneakyThrows
    public void givenMoveList_whenConvertToDtoList_thenReturnMoveDtoList() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();
        List<MoveDto> moveDto = List.of(MocksDto.createMove());
        List<Move> move = List.of(MocksDomain.createMove());

        when(converterType.convertToDto(Optional.of(type))).thenReturn(Optional.of(typeDto));

        List<MoveDto> result = converter.convertToDtoList(move);

        assertThat(result).isEqualTo(moveDto);

        verify(converterType, times(1)).convertToDto(Optional.of(type));
    }

    @Test
    @SneakyThrows
    public void givenMoveListEmpty_whenConvertToDtoList_thenReturnEmptyList() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        when(converterType.convertToDto(Optional.of(type))).thenReturn(Optional.of(typeDto));

        List<MoveDto> result = converter.convertToDtoList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);

        verify(converterType, times(0)).convertToDto(Optional.of(type));
    }

    @Test
    @SneakyThrows
    public void givenMoveDtoList_whenConvertToDomainList_thenReturnMoveList() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();
        List<MoveDto> moveDto = List.of(MocksDto.createMove());
        List<Move> move = List.of(MocksDomain.createMove());

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        List<Move> result = converter.convertToDomainList(moveDto);

        assertThat(result).isEqualTo(move);

        verify(converterType, times(1)).convertToDomain(Optional.of(typeDto));
    }

    @Test
    @SneakyThrows
    public void givenMoveDtoListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        TypeDto typeDto = MocksDto.createType();
        Type type = MocksDomain.createType();

        when(converterType.convertToDomain(Optional.of(typeDto))).thenReturn(Optional.of(type));

        List<Move> result = converter.convertToDomainList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);

        verify(converterType, times(0)).convertToDomain(Optional.of(typeDto));
    }

}

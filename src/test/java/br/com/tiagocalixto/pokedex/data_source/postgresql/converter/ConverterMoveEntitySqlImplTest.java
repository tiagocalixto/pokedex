package br.com.tiagocalixto.pokedex.data_source.postgresql.converter;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterMoveEntityImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.MoveEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksEntity;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ConverterMoveEntitySqlImplTest {

    @InjectMocks
    private ConverterMoveEntityImpl converter;

    @Mock
    private ConverterEntitySql<TypeEntity, Type> convertType;


    @Test
    @SneakyThrows
    void givenMoveOptional_whenConvertToEntity_thenReturnMoveEntityOptional() {

        MoveEntity moveEntity = MocksEntity.createMove();
        Move move = MocksDomain.createMove();

        when(convertType.convertToEntity(Optional.of(move.getType()))).thenReturn(Optional.of(moveEntity.getType()));

        Optional<MoveEntity> result = converter.convertToEntity(Optional.of(move));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(Util
                        .removeUndesirableChars(moveEntity.getDescription().toUpperCase()))
        );
    }

    @Test
    @SneakyThrows
    void givenMoveOptionalEmpty_whenConvertToEntity_thenReturnOptionalEmpty() {

        Optional<MoveEntity> result = converter.convertToEntity(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenMoveEntityOptional_whenConvertToDomain_thenReturnMoveOptional() {

        MoveEntity moveEntity = MocksEntity.createMove();
        Move move = MocksDomain.createMove();

        when(convertType.convertToDomain(Optional.of(moveEntity.getType()))).thenReturn(Optional.of(move.getType()));

        Optional<Move> result = converter.convertToDomain(Optional.of(moveEntity));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(move.getDescription()));
    }

    @Test
    @SneakyThrows
    void givenMoveEntityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Move> result = converter.convertToDomain(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenMove_whenConvertToEntityNotOptional_thenReturnMoveEntity() {

        MoveEntity moveEntity = MocksEntity.createMove();
        Move move = MocksDomain.createMove();

        when(convertType.convertToEntity(Optional.of(move.getType()))).thenReturn(Optional.of(moveEntity.getType()));

        MoveEntity result = converter.convertToEntityNotOptional(move);

        assertThat(result.getDescription()).isEqualTo(Util
                .removeUndesirableChars(moveEntity.getDescription().toUpperCase()));
    }

    @Test
    @SneakyThrows
    void givenMoveEntity_whenConvertToDomainNotOptional_thenReturnMove() {

        MoveEntity moveEntity = MocksEntity.createMove();
        Move move = MocksDomain.createMove();

        when(convertType.convertToDomain(Optional.of(moveEntity.getType()))).thenReturn(Optional.of(move.getType()));

        Move result = converter.convertToDomainNotOptional(moveEntity);

        assertThat(result.getDescription()).isEqualTo(move.getDescription());
    }


    @Test
    @SneakyThrows
    void givenMoveList_whenConvertToEntityList_thenReturnMoveEntityList() {

        List<Move> move = List.of(MocksDomain.createMove());
        MoveEntity moveEntity = MocksEntity.createMove();

        when(convertType.convertToEntity(Optional.of(move.get(0).getType())))
                .thenReturn(Optional.of(moveEntity.getType()));

        List<MoveEntity> result = converter.convertToEntityList(move);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenMoveListEmpty_whenConvertToEntityList_thenReturnEmptyList() {

        List<MoveEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenMoveEntityList_whenConvertToDomainList_thenReturnMoveList() {

        List<MoveEntity> moveEntity = List.of(MocksEntity.createMove());
        Move move = MocksDomain.createMove();

        when(convertType.convertToDomain(Optional.of(moveEntity.get(0).getType())))
                .thenReturn(Optional.of(move.getType()));

        List<Move> result = converter.convertToDomainList(moveEntity);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenMoveEntityListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<Move> result = converter.convertToDomainList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

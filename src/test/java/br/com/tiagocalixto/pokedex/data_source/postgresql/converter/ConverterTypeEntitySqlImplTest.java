package br.com.tiagocalixto.pokedex.data_source.postgresql.converter;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterTypeEntitySqlImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksEntity;
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

@ExtendWith(SpringExtension.class)
class ConverterTypeEntitySqlImplTest {

    @InjectMocks
    private ConverterTypeEntitySqlImpl converter;


    @Test
    @SneakyThrows
    void givenTypeOptional_whenConvertToEntity_thenReturnTypeEntityOptional() {

        TypeEntity typeEntity = MocksEntity.createType();
        Type type = MocksDomain.createType();

        Optional<TypeEntity> result = converter.convertToEntity(Optional.of(type));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(Util
                        .removeUndesirableChars(typeEntity.getDescription().toUpperCase()))
        );
    }

    @Test
    @SneakyThrows
    void givenTypeOptionalEmpty_whenConvertToEntity_thenReturnOptionalEmpty() {

        Optional<TypeEntity> result = converter.convertToEntity(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenTypeEntityOptional_whenConvertToDomain_thenReturnTypeOptional() {

        TypeEntity typeEntity = MocksEntity.createType();
        Type type = MocksDomain.createType();

        Optional<Type> result = converter.convertToDomain(Optional.of(typeEntity));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(type.getDescription()));
    }

    @Test
    @SneakyThrows
    void givenTypeEntityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Type> result = converter.convertToDomain(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenType_whenConvertToEntityNotOptional_thenReturnTypeEntity() {

        TypeEntity typeEntity = MocksEntity.createType();
        Type type = MocksDomain.createType();

        TypeEntity result = converter.convertToEntityNotOptional(type);

        assertThat(result.getDescription()).isEqualTo(Util
                .removeUndesirableChars(typeEntity.getDescription().toUpperCase()));
    }

    @Test
    @SneakyThrows
    void givenTypeEntity_whenConvertToDomainNotOptional_thenReturnType() {

        TypeEntity typeEntity = MocksEntity.createType();
        Type type = MocksDomain.createType();

        Type result = converter.convertToDomainNotOptional(typeEntity);

        assertThat(result.getDescription()).isEqualTo(type.getDescription());
    }


    @Test
    @SneakyThrows
    void givenTypeList_whenConvertToEntityList_thenReturnTypeEntityList() {

        List<Type> type = List.of(MocksDomain.createType());

        List<TypeEntity> result = converter.convertToEntityList(type);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenTypeListEmpty_whenConvertToEntityList_thenReturnEmptyList() {

        List<TypeEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenTypeEntityList_whenConvertToDomainList_thenReturnTypeList() {

        List<TypeEntity> typeEntity = List.of(MocksEntity.createType());

        List<Type> result = converter.convertToDomainList(typeEntity);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenTypeEntityListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<Type> result = converter.convertToDomainList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

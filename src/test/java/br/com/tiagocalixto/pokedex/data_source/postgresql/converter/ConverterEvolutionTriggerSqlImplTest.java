package br.com.tiagocalixto.pokedex.data_source.postgresql.converter;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterEvolutionTriggerSqlImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterEvolutionTriggerSqlImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionTriggerEntity;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
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
class ConverterEvolutionTriggerSqlImplTest {

    @InjectMocks
    private ConverterEvolutionTriggerSqlImpl converter;


    @Test
    @SneakyThrows
    void givenEvolutionTriggerOptional_whenConvertToEntity_thenReturnEvolutionTriggerEntityOptional() {

        EvolutionTriggerEntity entity = MocksEntity.createEvolutionTrigger();
        EvolutionTriggerEnum domain = EvolutionTriggerEnum.LEVEL_UP;

        Optional<EvolutionTriggerEntity> result = converter.convertToEntity(Optional.of(domain));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(entity.getDescription()));
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerOptionalEmpty_whenConvertToEntity_thenReturnOptionalLevelUp() {

        Optional<EvolutionTriggerEntity> result = converter.convertToEntity(Optional.empty());

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(EvolutionTriggerEnum.LEVEL_UP.toString()));
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerEntityOptional_whenConvertToDomain_thenReturnEvolutionTriggerOptional() {

        EvolutionTriggerEntity entity = MocksEntity.createEvolutionTrigger();
        EvolutionTriggerEnum domain = EvolutionTriggerEnum.LEVEL_UP;

        Optional<EvolutionTriggerEnum> result = converter.convertToDomain(Optional.of(entity));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.toString()).isEqualTo(domain.toString()));
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerEntityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<EvolutionTriggerEnum> result = converter.convertToDomain(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenEvolutionTrigger_whenConvertToEntityNotOptional_thenReturnEvolutionTriggerEntity() {

        EvolutionTriggerEntity entity = MocksEntity.createEvolutionTrigger();
        EvolutionTriggerEnum domain = EvolutionTriggerEnum.LEVEL_UP;

        EvolutionTriggerEntity result = converter.convertToEntityNotOptional(domain);

        assertThat(result.getDescription()).isEqualTo(entity.getDescription());
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerEntity_whenConvertToDomainNotOptional_thenReturnEvolutionTrigger() {

        EvolutionTriggerEntity entity = MocksEntity.createEvolutionTrigger();
        EvolutionTriggerEnum domain = EvolutionTriggerEnum.LEVEL_UP;

        EvolutionTriggerEnum result = converter.convertToDomainNotOptional(entity);

        assertThat(result.toString()).isEqualTo(domain.toString());
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerList_whenConvertToEntityList_thenReturnEvolutionTriggerEntityList() {

        List<EvolutionTriggerEnum> domain = List.of(EvolutionTriggerEnum.LEVEL_UP);

        List<EvolutionTriggerEntity> result = converter.convertToEntityList(domain);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerListEmpty_whenConvertToEntityList_thenReturnEmptyList() {

        List<EvolutionTriggerEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerEntityList_whenConvertToDomainList_thenReturnEvolutionTriggerList() {

        List<EvolutionTriggerEntity> domain = List.of(MocksEntity.createEvolutionTrigger());

        List<EvolutionTriggerEnum> result = converter.convertToDomainList(domain);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenEvolutionTriggerEntityListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<EvolutionTriggerEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

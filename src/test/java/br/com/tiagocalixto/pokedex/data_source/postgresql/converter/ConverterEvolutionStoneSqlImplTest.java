package br.com.tiagocalixto.pokedex.data_source.postgresql.converter;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterEvolutionStoneSqlImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionStoneEntity;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionStoneEnum;
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
class ConverterEvolutionStoneSqlImplTest {

    @InjectMocks
    private ConverterEvolutionStoneSqlImpl converter;


    @Test
    @SneakyThrows
    void givenEvolutionStoneOptional_whenConvertToEntity_thenReturnEvolutionStoneEntityOptional() {

        EvolutionStoneEntity entity = MocksEntity.createEvolutionStone();
        EvolutionStoneEnum domain = EvolutionStoneEnum.FIRE_STONE;

        Optional<EvolutionStoneEntity> result = converter.convertToEntity(Optional.of(domain));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(entity.getDescription()));
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneOptionalEmpty_whenConvertToEntity_thenReturnOptionalEmpty() {

        Optional<EvolutionStoneEntity> result = converter.convertToEntity(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneEntityOptional_whenConvertToDomain_thenReturnEvolutionStoneOptional() {

        EvolutionStoneEntity entity = MocksEntity.createEvolutionStone();
        EvolutionStoneEnum domain = EvolutionStoneEnum.FIRE_STONE;

        Optional<EvolutionStoneEnum> result = converter.convertToDomain(Optional.of(entity));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.toString()).isEqualTo(domain.toString()));
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneEntityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<EvolutionStoneEnum> result = converter.convertToDomain(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenEvolutionStone_whenConvertToEntityNotOptional_thenReturnEvolutionStoneEntity() {

        EvolutionStoneEntity entity = MocksEntity.createEvolutionStone();
        EvolutionStoneEnum domain = EvolutionStoneEnum.FIRE_STONE;

        EvolutionStoneEntity result = converter.convertToEntityNotOptional(domain);

        assertThat(result.getDescription()).isEqualTo(entity.getDescription());
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneEntity_whenConvertToDomainNotOptional_thenReturnEvolutionStone() {

        EvolutionStoneEntity entity = MocksEntity.createEvolutionStone();
        EvolutionStoneEnum domain = EvolutionStoneEnum.FIRE_STONE;

        EvolutionStoneEnum result = converter.convertToDomainNotOptional(entity);

        assertThat(result.toString()).isEqualTo(domain.toString());
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneList_whenConvertToEntityList_thenReturnEvolutionStoneEntityList() {

        List<EvolutionStoneEnum> domain = List.of(EvolutionStoneEnum.FIRE_STONE);

        List<EvolutionStoneEntity> result = converter.convertToEntityList(domain);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneListEmpty_whenConvertToEntityList_thenReturnEmptyList() {

        List<EvolutionStoneEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneEntityList_whenConvertToDomainList_thenReturnEvolutionStoneList() {

        List<EvolutionStoneEntity> domain = List.of(MocksEntity.createEvolutionStone());

        List<EvolutionStoneEnum> result = converter.convertToDomainList(domain);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenEvolutionStoneEntityListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<EvolutionStoneEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

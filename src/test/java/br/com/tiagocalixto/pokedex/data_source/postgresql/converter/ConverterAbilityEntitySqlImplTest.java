package br.com.tiagocalixto.pokedex.data_source.postgresql.converter;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterAbilityEntitySqlImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.AbilityEntity;
import br.com.tiagocalixto.pokedex.domain.Ability;
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
class ConverterAbilityEntitySqlImplTest {

    @InjectMocks
    private ConverterAbilityEntitySqlImpl converter;


    @Test
    @SneakyThrows
    void givenAbilityOptional_whenConvertToEntity_thenReturnAbilityEntityOptional() {

        AbilityEntity abilityEntity = MocksEntity.createAbility();
        Ability ability = MocksDomain.createAbility();

        Optional<AbilityEntity> result = converter.convertToEntity(Optional.of(ability));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(Util
                        .removeUndesirableChars(abilityEntity.getDescription().toUpperCase()))
        );
    }

    @Test
    @SneakyThrows
    void givenAbilityOptionalEmpty_whenConvertToEntity_thenReturnOptionalEmpty() {

        Optional<AbilityEntity> result = converter.convertToEntity(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenAbilityEntityOptional_whenConvertToDomain_thenReturnAbilityOptional() {

        AbilityEntity abilityEntity = MocksEntity.createAbility();
        Ability ability = MocksDomain.createAbility();

        Optional<Ability> result = converter.convertToDomain(Optional.of(abilityEntity));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getDescription()).isEqualTo(ability.getDescription()));
    }

    @Test
    @SneakyThrows
    void givenAbilityEntityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Ability> result = converter.convertToDomain(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenAbility_whenConvertToEntityNotOptional_thenReturnAbilityEntity() {

        AbilityEntity abilityEntity = MocksEntity.createAbility();
        Ability ability = MocksDomain.createAbility();

        AbilityEntity result = converter.convertToEntityNotOptional(ability);

        assertThat(result.getDescription()).isEqualTo(Util
                .removeUndesirableChars(abilityEntity.getDescription().toUpperCase()));
    }

    @Test
    @SneakyThrows
    void givenAbilityEntity_whenConvertToDomainNotOptional_thenReturnAbility() {

        AbilityEntity abilityEntity = MocksEntity.createAbility();
        Ability ability = MocksDomain.createAbility();

        Ability result = converter.convertToDomainNotOptional(abilityEntity);

        assertThat(result.getDescription()).isEqualTo(ability.getDescription());
    }


    @Test
    @SneakyThrows
    void givenAbilityList_whenConvertToEntityList_thenReturnAbilityEntityList() {

        List<Ability> ability = List.of(MocksDomain.createAbility());

        List<AbilityEntity> result = converter.convertToEntityList(ability);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenAbilityListEmpty_whenConvertToEntityList_thenReturnEmptyList() {

        List<AbilityEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }

    @Test
    @SneakyThrows
    void givenAbilityEntityList_whenConvertToDomainList_thenReturnAbilityList() {

        List<AbilityEntity> abilityEntity = List.of(MocksEntity.createAbility());

        List<Ability> result = converter.convertToDomainList(abilityEntity);

        assertTrue(result.size() > 0);
    }

    @Test
    @SneakyThrows
    void givenAbilityEntityListEmpty_whenConvertToDomainList_thenReturnEmptyList() {

        List<Ability> result = converter.convertToDomainList(Collections.emptyList());

        assertThat(Collections.emptyList()).isEqualTo(result);
    }
}

package br.com.tiagocalixto.pokedex.data_source.postgresql.converter;


import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl.ConverterPokemonEntityImpl;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionStoneEnum;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ConverterPokemonEntitySqlImplTest {

    @InjectMocks
    private ConverterPokemonEntityImpl converter;

    @Mock
    private ConverterEntitySql<TypeEntity, Type> convertType;

    @Mock
    private ConverterEntitySql<AbilityEntity, Ability> convertAbility;

    @Mock
    private ConverterEntitySql<MoveEntity, Move> convertMove;

    @Mock
    private ConverterEntitySql<EvolutionTriggerEntity, EvolutionTriggerEnum> convertTrigger;

    @Mock
    private ConverterEntitySql<EvolutionStoneEntity, EvolutionStoneEnum> convertItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(converter, "convertType", convertType);
        ReflectionTestUtils.setField(converter, "convertAbility", convertAbility);
        ReflectionTestUtils.setField(converter, "convertMove", convertMove);
        ReflectionTestUtils.setField(converter, "convertTrigger", convertTrigger);
        ReflectionTestUtils.setField(converter, "convertItem", convertItem);
    }


    @Test
    @SneakyThrows
    void givenPokemonOptional_whenConvertToEntity_thenReturnPokemonEntityOptional() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToEntity(Optional.of(pokemon.getType().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getType().get(0).getType()));
        when(convertTrigger.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        when(convertItem.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        when(convertAbility.convertToEntity(Optional.of(pokemon.getAbility().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        when(convertMove.convertToEntity(Optional.of(pokemon.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemonEntity.getMove().get(0).getMove()));

        Optional<PokemonEntity> result = converter.convertToEntity(Optional.of(pokemon));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getName()).isEqualTo(pokemon.getName())
        );

        verify(convertType, times(4)).convertToEntity(Optional.of(pokemon.getType().get(0)));
        verify(convertTrigger, times(1))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        verify(convertItem, times(1))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem()));
        verify(convertAbility, times(1))
                .convertToEntity(Optional.of(pokemon.getAbility().get(0)));
        verify(convertMove, times(1))
                .convertToEntity(Optional.of(pokemon.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonOptionalEmpty_whenConvertToEntity_thenReturnOptionalEmpty() {

        Optional<PokemonEntity> result = converter.convertToEntity(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenPokemonEntityOptional_whenConvertToDomain_thenReturnPokemonOptional() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType())))
                .thenReturn(Optional.of(pokemon.getType().get(0)));
        when(convertTrigger.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        when(convertItem.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getItem()));
        when(convertAbility.convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility())))
                .thenReturn(Optional.of(pokemon.getAbility().get(0)));
        when(convertMove.convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemon.getMove().get(0).getMove()));

        Optional<Pokemon> result = converter.convertToDomain(Optional.of(pokemonEntity));

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getName()).isEqualTo(pokemonEntity.getName())
        );

        verify(convertType, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType()));
        verify(convertTrigger, times(2))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        verify(convertItem, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        verify(convertAbility, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        verify(convertMove, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonEntityOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Pokemon> result = converter.convertToDomain(Optional.empty());

        assertTrue(result.isEmpty());
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenConvertToEntityNotOptional_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToEntity(Optional.of(pokemon.getType().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getType().get(0).getType()));
        when(convertTrigger.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        when(convertItem.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        when(convertAbility.convertToEntity(Optional.of(pokemon.getAbility().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        when(convertMove.convertToEntity(Optional.of(pokemon.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemonEntity.getMove().get(0).getMove()));

        PokemonEntity result = converter.convertToEntityNotOptional(pokemon);

        assertThat(result.getName()).isEqualTo(pokemon.getName());

        verify(convertType, times(4)).convertToEntity(Optional.of(pokemon.getType().get(0)));
        verify(convertTrigger, times(1))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        verify(convertItem, times(1))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem()));
        verify(convertAbility, times(1))
                .convertToEntity(Optional.of(pokemon.getAbility().get(0)));
        verify(convertMove, times(1))
                .convertToEntity(Optional.of(pokemon.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonEntity_whenConvertToDomainNotOptional_thenReturnPokemon() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType())))
                .thenReturn(Optional.of(pokemon.getType().get(0)));
        when(convertTrigger.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        when(convertItem.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getItem()));
        when(convertAbility.convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility())))
                .thenReturn(Optional.of(pokemon.getAbility().get(0)));
        when(convertMove.convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemon.getMove().get(0).getMove()));

        Pokemon result = converter.convertToDomainNotOptional(pokemonEntity);

        assertThat(result.getName()).isEqualTo(pokemonEntity.getName());

        verify(convertType, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType()));
        verify(convertTrigger, times(2))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        verify(convertItem, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        verify(convertAbility, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        verify(convertMove, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonList_whenConvertToEntityList_thenReturnPokemonEntityList() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToEntity(Optional.of(pokemon.getType().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getType().get(0).getType()));
        when(convertTrigger.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        when(convertItem.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        when(convertAbility.convertToEntity(Optional.of(pokemon.getAbility().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        when(convertMove.convertToEntity(Optional.of(pokemon.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemonEntity.getMove().get(0).getMove()));

        List<PokemonEntity> result = converter.convertToEntityList(List.of(pokemon));

        assertFalse(result.isEmpty());
        assertThat(result.get(0).getName()).isEqualTo(pokemon.getName());

        verify(convertType, times(4)).convertToEntity(Optional.of(pokemon.getType().get(0)));
        verify(convertTrigger, times(1))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        verify(convertItem, times(1))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem()));
        verify(convertAbility, times(1))
                .convertToEntity(Optional.of(pokemon.getAbility().get(0)));
        verify(convertMove, times(1))
                .convertToEntity(Optional.of(pokemon.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenEmptyList_whenConvertToEntityList_thenReturnEmptyList() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToEntity(Optional.of(pokemon.getType().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getType().get(0).getType()));
        when(convertTrigger.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        when(convertItem.convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem())))
                .thenReturn(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        when(convertAbility.convertToEntity(Optional.of(pokemon.getAbility().get(0))))
                .thenReturn(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        when(convertMove.convertToEntity(Optional.of(pokemon.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemonEntity.getMove().get(0).getMove()));

        List<PokemonEntity> result = converter.convertToEntityList(Collections.emptyList());

        assertTrue(result.isEmpty());

        verify(convertType, times(0)).convertToEntity(Optional.of(pokemon.getType().get(0)));
        verify(convertTrigger, times(0))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        verify(convertItem, times(0))
                .convertToEntity(Optional.of(pokemon.getEvolvedFrom().getItem()));
        verify(convertAbility, times(0))
                .convertToEntity(Optional.of(pokemon.getAbility().get(0)));
        verify(convertMove, times(0))
                .convertToEntity(Optional.of(pokemon.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonEntityList_whenConvertToDomainList_thenReturnPokemonList() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType())))
                .thenReturn(Optional.of(pokemon.getType().get(0)));
        when(convertTrigger.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        when(convertItem.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getItem()));
        when(convertAbility.convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility())))
                .thenReturn(Optional.of(pokemon.getAbility().get(0)));
        when(convertMove.convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemon.getMove().get(0).getMove()));

        List<Pokemon> result = converter.convertToDomainList(List.of(pokemonEntity));

        assertFalse(result.isEmpty());
        assertThat(result.get(0).getName()).isEqualTo(pokemonEntity.getName());

        verify(convertType, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType()));
        verify(convertTrigger, times(2))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        verify(convertItem, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        verify(convertAbility, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        verify(convertMove, times(1))
                .convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove()));
    }

    @Test
    @SneakyThrows
    void givenEmptyList_whenConvertToDomainList_thenReturnEmptyList() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon();
        pokemon.getEvolvedFrom().setItem(EvolutionStoneEnum.THUNDER_STONE);
        pokemonEntity.getEvolvedFrom().get(0).setEvolutionItem(MocksEntity.createEvolutionStone());

        when(convertType.convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType())))
                .thenReturn(Optional.of(pokemon.getType().get(0)));
        when(convertTrigger.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getTrigger()));
        when(convertItem.convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem())))
                .thenReturn(Optional.of(pokemon.getEvolvedFrom().getItem()));
        when(convertAbility.convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility())))
                .thenReturn(Optional.of(pokemon.getAbility().get(0)));
        when(convertMove.convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove())))
                .thenReturn(Optional.of(pokemon.getMove().get(0).getMove()));

        List<Pokemon> result = converter.convertToDomainList(Collections.emptyList());

        assertTrue(result.isEmpty());

        verify(convertType, times(0))
                .convertToDomain(Optional.of(pokemonEntity.getType().get(0).getType()));
        verify(convertTrigger, times(0))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionTrigger()));
        verify(convertItem, times(0))
                .convertToDomain(Optional.of(pokemonEntity.getEvolvedFrom().get(0).getEvolutionItem()));
        verify(convertAbility, times(0))
                .convertToDomain(Optional.of(pokemonEntity.getAbility().get(0).getAbility()));
        verify(convertMove, times(0))
                .convertToDomain(Optional.of(pokemonEntity.getMove().get(0).getMove()));
    }
}

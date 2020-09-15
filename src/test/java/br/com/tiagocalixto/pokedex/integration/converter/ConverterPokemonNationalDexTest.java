package br.com.tiagocalixto.pokedex.integration.converter;

import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl.ConverterPokemonNationalDexImpl;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksNationalDexDto;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ConverterPokemonNationalDexTest {

    @InjectMocks
    private ConverterPokemonNationalDexImpl converter;

    @Mock
    private ConverterNationalDex<AbilityNationalDexDto, Ability> abilityConverter;

    @Mock
    private ConverterNationalDex<MoveNationalDexDto, Move> moveConverter;

    @Mock
    private ConverterNationalDex<TypeNationalDexDto, Type> typeConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(converter, "abilityConverter", abilityConverter);
        ReflectionTestUtils.setField(converter, "moveConverter", moveConverter);
        ReflectionTestUtils.setField(converter, "typeConverter", typeConverter);
    }


    @Test
    @SneakyThrows
    void givenPokemonOptional_whenConvertToDomain_thenReturnPokemonOptional() {

        PokemonNationalDexDto dto = MocksNationalDexDto.createPokemon();
        MoveNationalDexDto moveDto = MocksNationalDexDto.createMove();
        Type type = MocksDomain.createType();
        Move move = MocksDomain.createMove();
        Ability ability = MocksDomain.createAbility();

        when(typeConverter.convertToDomainList(anyList())).thenReturn(List.of(type));
        when(moveConverter.convertToDomain(Optional.of(moveDto))).thenReturn(Optional.of(move));
        when(abilityConverter.convertToDomainList(anyList())).thenReturn(List.of(ability));

        Optional<Pokemon> result = converter.convertToDomain(Optional.of(dto));

        assertTrue(result.isPresent());

        result.ifPresent(item -> {
            assertThat(item.getName()).isEqualTo(dto.getName());
            assertThat(item.getNumber()).isEqualTo(dto.getNumber());
            assertThat(item.getId()).isEqualTo(dto.getId());
        });
    }

    @Test
    @SneakyThrows
    void givenPokemonOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        Optional<Pokemon> result = converter.convertToDomain(Optional.empty());

        assertFalse(result.isPresent());
    }

    @Test
    @SneakyThrows
    void givenMoveList_whenConvertToDomainList_thenReturnMoveList() {

        PokemonNationalDexDto dto = MocksNationalDexDto.createPokemon();
        MoveNationalDexDto moveDto = MocksNationalDexDto.createMove();
        Type type = MocksDomain.createType();
        Move move = MocksDomain.createMove();
        Ability ability = MocksDomain.createAbility();

        when(typeConverter.convertToDomainList(anyList())).thenReturn(List.of(type));
        when(moveConverter.convertToDomain(Optional.of(moveDto))).thenReturn(Optional.of(move));
        when(abilityConverter.convertToDomainList(anyList())).thenReturn(List.of(ability));

        List<Pokemon> result = converter.convertToDomainList(List.of(dto));

        assertFalse(result.isEmpty());

        assertThat(result.get(0).getName()).isEqualTo(dto.getName());
        assertThat(result.get(0).getNumber()).isEqualTo(dto.getNumber());
        assertThat(result.get(0).getId()).isEqualTo(dto.getId());
    }

    @Test
    @SneakyThrows
    void givenListEmpty_whenConvertToDomainList_thenReturnListEmpty() {

        List<Pokemon> result = converter.convertToDomainList(Collections.emptyList());

        assertTrue(result.isEmpty());
    }
}
package br.com.tiagocalixto.pokedex.controller.converter;

import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterAbilityDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterMoveDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterPokemonDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.converter.impl.ConverterTypeDtoImpl;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class ConverterPokemonDtoTest {

    @Mock(name = "type")
    private ConverterTypeDtoImpl converterType;

    @Mock(name = "move")
    private ConverterMoveDtoImpl converterMove;

    @Mock(name = "ability")
    private ConverterAbilityDtoImpl converterAbility;

    @InjectMocks
    private ConverterPokemonDtoImpl converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(converter, "convertType", converterType);
        ReflectionTestUtils.setField(converter, "convertMove", converterMove);
        ReflectionTestUtils.setField(converter, "convertAbility", converterAbility);
    }


    @Test
    @SneakyThrows
    void givenPokemonOptional_whenConvertToDto_thenReturnPokemonDtoOptional() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDtoList(anyList())).thenReturn(List.of(MocksDto.createType()));
        when(converterMove.convertToDto(Optional.of(MocksDomain.createMove())))
                .thenReturn(Optional.of(MocksDto.createMove()));
        when(converterAbility.convertToDtoList(pokemon.getAbility())).thenReturn(pokemonDto.getAbility());

        Optional<PokemonDto> result = converter.convertToDto(Optional.of(pokemon));

        result.ifPresent(item -> {
            assertThat(item.getName()).isEqualTo(pokemonDto.getName());
            assertThat(item.getNumber()).isEqualTo(pokemonDto.getNumber());
            assertThat(item.getAbout()).isEqualTo(pokemonDto.getAbout());
            assertThat(item.getId()).isEqualTo(pokemonDto.getId());
            assertThat(item.getHeight()).isEqualTo(pokemonDto.getHeight());
            assertThat(item.getWeight()).isEqualTo(pokemonDto.getWeight());
            assertThat(item.getStats()).isEqualTo(pokemonDto.getStats());
            assertThat(item.getType()).isEqualTo(pokemonDto.getType());
            assertThat(item.getMove()).isEqualTo(pokemonDto.getMove());
            assertThat(item.getAbility()).isEqualTo(pokemonDto.getAbility());
        });

        verify(converterType, times(4)).convertToDtoList(anyList());
        verify(converterAbility, times(1))
                .convertToDtoList(pokemon.getAbility());
        verify(converterMove, times(1)).convertToDto(Optional.of(MocksDomain.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonDtoOptional_whenConvertToDomain_thenReturnPokemonOptional() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDomainList(anyList())).thenReturn(List.of(MocksDomain.createType()));
        when(converterMove.convertToDomain(Optional.of(MocksDto.createMove())))
                .thenReturn(Optional.of(MocksDomain.createMove()));
        when(converterAbility.convertToDomainList(pokemonDto.getAbility())).thenReturn(pokemon.getAbility());

        Optional<Pokemon> result = converter.convertToDomain(Optional.of(pokemonDto));

        result.ifPresent(item -> {
            assertThat(item.getName()).isEqualTo(pokemon.getName());
            assertThat(item.getNumber()).isEqualTo(pokemon.getNumber());
            assertThat(item.getAbout()).isEqualTo(pokemon.getAbout());
            assertThat(item.getId()).isEqualTo(pokemon.getId());
            assertThat(item.getHeight()).isEqualTo(pokemon.getHeight());
            assertThat(item.getWeight()).isEqualTo(pokemon.getWeight());
            assertThat(item.getStats()).isEqualTo(pokemon.getStats());
            assertThat(item.getType()).isEqualTo(pokemon.getType());
            assertThat(item.getMove()).isEqualTo(pokemon.getMove());
            assertThat(item.getAbility()).isEqualTo(pokemon.getAbility());
        });

        verify(converterType, times(2)).convertToDomainList(anyList());
        verify(converterAbility, times(1))
                .convertToDomainList(pokemonDto.getAbility());
        verify(converterMove, times(1)).convertToDomain(Optional.of(MocksDto.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonOptionalEmpty_whenConvertToDto_thenReturnOptionalEmpty() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDtoList(anyList())).thenReturn(List.of(MocksDto.createType()));
        when(converterMove.convertToDto(Optional.of(MocksDomain.createMove())))
                .thenReturn(Optional.of(MocksDto.createMove()));
        when(converterAbility.convertToDtoList(pokemon.getAbility())).thenReturn(pokemonDto.getAbility());

        Optional<PokemonDto> result = converter.convertToDto(Optional.empty());

        assertThat(result).isEqualTo(Optional.empty());

        verify(converterType, times(0)).convertToDtoList(anyList());
        verify(converterAbility, times(0))
                .convertToDtoList(pokemon.getAbility());
        verify(converterMove, times(0)).convertToDto(Optional.of(MocksDomain.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonDtoOptionalEmpty_whenConvertToDomain_thenReturnOptionalEmpty() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDomainList(anyList())).thenReturn(List.of(MocksDomain.createType()));
        when(converterMove.convertToDomain(Optional.of(MocksDto.createMove())))
                .thenReturn(Optional.of(MocksDomain.createMove()));
        when(converterAbility.convertToDomainList(pokemonDto.getAbility())).thenReturn(pokemon.getAbility());

        Optional<Pokemon> result = converter.convertToDomain(Optional.empty());

        assertThat(result).isEqualTo(Optional.empty());

        verify(converterType, times(0)).convertToDomainList(anyList());
        verify(converterAbility, times(0))
                .convertToDomainList(pokemonDto.getAbility());
        verify(converterMove, times(0)).convertToDomain(Optional.of(MocksDto.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenConvertToDtoNotOptional_thenReturnPokemonDto() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDtoList(anyList())).thenReturn(List.of(MocksDto.createType()));
        when(converterMove.convertToDto(Optional.of(MocksDomain.createMove())))
                .thenReturn(Optional.of(MocksDto.createMove()));
        when(converterAbility.convertToDtoList(pokemon.getAbility())).thenReturn(pokemonDto.getAbility());

        PokemonDto result = converter.convertToDtoNotOptional(pokemon);

        assertThat(result.getName()).isEqualTo(pokemonDto.getName());
        assertThat(result.getNumber()).isEqualTo(pokemonDto.getNumber());
        assertThat(result.getAbout()).isEqualTo(pokemonDto.getAbout());
        assertThat(result.getId()).isEqualTo(pokemonDto.getId());
        assertThat(result.getHeight()).isEqualTo(pokemonDto.getHeight());
        assertThat(result.getWeight()).isEqualTo(pokemonDto.getWeight());
        assertThat(result.getStats()).isEqualTo(pokemonDto.getStats());
        assertThat(result.getType()).isEqualTo(pokemonDto.getType());
        assertThat(result.getMove()).isEqualTo(pokemonDto.getMove());
        assertThat(result.getAbility()).isEqualTo(pokemonDto.getAbility());

        verify(converterType, times(4)).convertToDtoList(anyList());
        verify(converterAbility, times(1))
                .convertToDtoList(pokemon.getAbility());
        verify(converterMove, times(1)).convertToDto(Optional.of(MocksDomain.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonDto_whenConvertToDomainNotOptional_thenReturnPokemon() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDomainList(anyList())).thenReturn(List.of(MocksDomain.createType()));
        when(converterMove.convertToDomain(Optional.of(MocksDto.createMove())))
                .thenReturn(Optional.of(MocksDomain.createMove()));
        when(converterAbility.convertToDomainList(pokemonDto.getAbility())).thenReturn(pokemon.getAbility());

        Pokemon result = converter.convertToDomainNotOptional(pokemonDto);

        assertThat(result.getName()).isEqualTo(pokemon.getName());
        assertThat(result.getNumber()).isEqualTo(pokemon.getNumber());
        assertThat(result.getAbout()).isEqualTo(pokemon.getAbout());
        assertThat(result.getId()).isEqualTo(pokemon.getId());
        assertThat(result.getHeight()).isEqualTo(pokemon.getHeight());
        assertThat(result.getWeight()).isEqualTo(pokemon.getWeight());
        assertThat(result.getStats()).isEqualTo(pokemon.getStats());
        assertThat(result.getType()).isEqualTo(pokemon.getType());
        assertThat(result.getMove()).isEqualTo(pokemon.getMove());
        assertThat(result.getAbility()).isEqualTo(pokemon.getAbility());

        verify(converterType, times(2)).convertToDomainList(anyList());
        verify(converterAbility, times(1))
                .convertToDomainList(pokemonDto.getAbility());
        verify(converterMove, times(1)).convertToDomain(Optional.of(MocksDto.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonList_whenConvertToDtoList_thenReturnPokemonDtoList() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDtoList(anyList())).thenReturn(List.of(MocksDto.createType()));
        when(converterMove.convertToDto(Optional.of(MocksDomain.createMove())))
                .thenReturn(Optional.of(MocksDto.createMove()));
        when(converterAbility.convertToDtoList(pokemon.getAbility())).thenReturn(pokemonDto.getAbility());

        List<PokemonDto> result = converter.convertToDtoList(List.of(pokemon));

        result.forEach(item -> {
            assertThat(item.getName()).isEqualTo(pokemonDto.getName());
            assertThat(item.getNumber()).isEqualTo(pokemonDto.getNumber());
            assertThat(item.getAbout()).isEqualTo(pokemonDto.getAbout());
            assertThat(item.getId()).isEqualTo(pokemonDto.getId());
            assertThat(item.getHeight()).isEqualTo(pokemonDto.getHeight());
            assertThat(item.getWeight()).isEqualTo(pokemonDto.getWeight());
            assertThat(item.getStats()).isEqualTo(pokemonDto.getStats());
            assertThat(item.getType()).isEqualTo(pokemonDto.getType());
            assertThat(item.getMove()).isEqualTo(pokemonDto.getMove());
            assertThat(item.getAbility()).isEqualTo(pokemonDto.getAbility());
        });

        verify(converterType, times(4)).convertToDtoList(anyList());
        verify(converterAbility, times(1))
                .convertToDtoList(pokemon.getAbility());
        verify(converterMove, times(1)).convertToDto(Optional.of(MocksDomain.createMove()));
    }

    @Test
    @SneakyThrows
    void givenPokemonDtoList_whenConvertToDomainList_thenReturnPokemonList() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(converterType.convertToDomainList(anyList())).thenReturn(List.of(MocksDomain.createType()));
        when(converterMove.convertToDomain(Optional.of(MocksDto.createMove())))
                .thenReturn(Optional.of(MocksDomain.createMove()));
        when(converterAbility.convertToDomainList(pokemonDto.getAbility())).thenReturn(pokemon.getAbility());

        List<Pokemon> result = converter.convertToDomainList(List.of(pokemonDto));

        result.forEach(item -> {
            assertThat(item.getName()).isEqualTo(pokemon.getName());
            assertThat(item.getNumber()).isEqualTo(pokemon.getNumber());
            assertThat(item.getAbout()).isEqualTo(pokemon.getAbout());
            assertThat(item.getId()).isEqualTo(pokemon.getId());
            assertThat(item.getHeight()).isEqualTo(pokemon.getHeight());
            assertThat(item.getWeight()).isEqualTo(pokemon.getWeight());
            assertThat(item.getStats()).isEqualTo(pokemon.getStats());
            assertThat(item.getType()).isEqualTo(pokemon.getType());
            assertThat(item.getMove()).isEqualTo(pokemon.getMove());
            assertThat(item.getAbility()).isEqualTo(pokemon.getAbility());
        });

        verify(converterType, times(2)).convertToDomainList(anyList());
        verify(converterAbility, times(1))
                .convertToDomainList(pokemonDto.getAbility());
        verify(converterMove, times(1)).convertToDomain(Optional.of(MocksDto.createMove()));
    }
}

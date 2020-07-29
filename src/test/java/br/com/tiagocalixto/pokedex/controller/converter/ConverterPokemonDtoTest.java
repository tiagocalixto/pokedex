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
public class ConverterPokemonDtoTest {

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
    public void givenPokemonOptional_whenConvertToDto_thenReturnPokemonDtoOptional() {

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
}

package br.com.tiagocalixto.pokedex.controller.adapter;

import br.com.tiagocalixto.pokedex.controller.v1.adapter.PokemonControllerAdapter;
import br.com.tiagocalixto.pokedex.controller.v1.converter.ConverterDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import br.com.tiagocalixto.pokedex.use_case.handler.HandlerUseCase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PokemonControllerAdapterTest {

    @InjectMocks
    PokemonControllerAdapter adapter;

    @Mock
    private HandlerUseCase handler;

    @Mock
    private ConverterDto<PokemonDto, Pokemon> converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @SneakyThrows
    void givenId_whenFindById_thenReturnPokemonDto() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(handler.findPokemonById(anyLong())).thenReturn(pokemon);
        when(converter.convertToDtoNotOptional(pokemon)).thenReturn(pokemonDto);

        PokemonDto result = adapter.getOneById(pokemon.getId());

        assertThat(result).isEqualTo(pokemonDto);

        verify(handler, times(1)).findPokemonById(anyLong());
        verify(converter, times(1)).convertToDtoNotOptional(any(Pokemon.class));
    }


    @Test
    @SneakyThrows
    void givenNumber_whenFindByNumber_thenReturnPokemonDto() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(handler.findPokemonByNumber(anyLong())).thenReturn(pokemon);
        when(converter.convertToDtoNotOptional(pokemon)).thenReturn(pokemonDto);

        PokemonDto result = adapter.getOneByNumber(pokemon.getNumber());

        assertThat(result).isEqualTo(pokemonDto);

        verify(handler, times(1)).findPokemonByNumber(anyLong());
        verify(converter, times(1)).convertToDtoNotOptional(any(Pokemon.class));
    }

    @Test
    @SneakyThrows
    void givenName_whenFindAllByName_thenReturnPokemonDtoList() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(handler.findPokemonByName(anyString())).thenReturn(List.of(pokemon));
        when(converter.convertToDtoList(List.of(pokemon))).thenReturn(List.of(pokemonDto));

        List<PokemonDto> result = adapter.getAllByName(pokemon.getName());

        result.forEach(item -> assertThat(item).isEqualTo(pokemonDto));

        verify(handler, times(1)).findPokemonByName(anyString());
        verify(converter, times(1)).convertToDtoList(anyList());
    }

    @Test
    @SneakyThrows
    void givenPageNumber_whenFindPage_thenReturnPokemonDtoList() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(handler.findPokemonPageable(anyInt())).thenReturn(List.of(pokemon));
        when(converter.convertToDtoList(List.of(pokemon))).thenReturn(List.of(pokemonDto));

        List<PokemonDto> result = adapter.getPage(1);

        result.forEach(item -> assertThat(item).isEqualTo(pokemonDto));

        verify(handler, times(1)).findPokemonPageable(anyInt());
        verify(converter, times(1)).convertToDtoList(anyList());
    }

    @Test
    @SneakyThrows
    void givenPokemonDto_whenSave_thenReturnPokemonDto() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(handler.savePokemon(any(Pokemon.class))).thenReturn(pokemon);
        when(converter.convertToDomainNotOptional(any(PokemonDto.class))).thenReturn(pokemon);
        when(converter.convertToDtoNotOptional(any(Pokemon.class))).thenReturn(pokemonDto);

        PokemonDto result = adapter.save(pokemonDto);

        assertThat(result).isEqualTo(pokemonDto);

        verify(handler, times(1)).savePokemon(any(Pokemon.class));
        verify(converter, times(1)).convertToDomainNotOptional(any(PokemonDto.class));
        verify(converter, times(1)).convertToDtoNotOptional(any(Pokemon.class));
    }

    @Test
    @SneakyThrows
    void givenPokemonDto_whenUpdate_thenReturnPokemonDto() {

        PokemonDto pokemonDto = MocksDto.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonDto.getId());

        when(handler.updatePokemon(any(Pokemon.class))).thenReturn(pokemon);
        when(converter.convertToDomainNotOptional(any(PokemonDto.class))).thenReturn(pokemon);
        when(converter.convertToDtoNotOptional(any(Pokemon.class))).thenReturn(pokemonDto);

        PokemonDto result = adapter.update(pokemonDto);

        assertThat(result).isEqualTo(pokemonDto);

        verify(handler, times(1)).updatePokemon(any(Pokemon.class));
        verify(converter, times(1)).convertToDomainNotOptional(any(PokemonDto.class));
        verify(converter, times(1)).convertToDtoNotOptional(any(Pokemon.class));
    }

    @Test
    @SneakyThrows
    void givenId_whenDelete_thenDeletePokemonVoid() {

        PokemonDto pokemonDto = MocksDto.createPokemon();

        doNothing().when(handler).deletePokemonById(anyLong());

        adapter.deleteById(pokemonDto.getId());

        verify(handler, times(1)).deletePokemonById(anyLong());
    }
}

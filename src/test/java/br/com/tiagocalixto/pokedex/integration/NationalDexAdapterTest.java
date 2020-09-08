package br.com.tiagocalixto.pokedex.integration;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.integration.national_dex.adapter.NationalDexAdapter;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.facade.GetPokemonFromApiFacade;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksNationalDex;
import br.com.tiagocalixto.pokedex.mock.MocksNationalDexDto;
import lombok.SneakyThrows;
import me.sargunvohra.lib.pokekotlin.model.EvolutionChain;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class NationalDexAdapterTest {

    @InjectMocks
    private NationalDexAdapter adapter;

    @Mock
    private GetPokemonFromApiFacade getFromNationalDex;

    @Mock
    private ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter;


    @Test
    @SneakyThrows
    void givenId_whenFindById_thenReturnPokemonOptional() {

        Pokemon pokemon = MocksDomain.createPokemon();
        PokemonNationalDexDto nationalDexDto = MocksNationalDexDto.createPokemon();

        when(getFromNationalDex.getPokemon(anyLong())).thenReturn(Optional.of(nationalDexDto));
        when(converter.convertToDomain(Optional.of(nationalDexDto))).thenReturn(Optional.of(pokemon));

        Optional<Pokemon> result = adapter.findById(pokemon.getId());

        assertTrue(result.isPresent());
        result.ifPresent(item ->
                assertThat(item.getName()).isEqualTo(pokemon.getName())
                );

        verify(getFromNationalDex, times(1)).getPokemon(anyLong());
        verify(converter,times(1)).convertToDomain(Optional.of(nationalDexDto));
    }
}

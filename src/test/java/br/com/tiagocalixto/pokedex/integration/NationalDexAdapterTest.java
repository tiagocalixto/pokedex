package br.com.tiagocalixto.pokedex.integration;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.integration.national_dex.adapter.NationalDexAdapter;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.facade.GetPokemonFromApiFacade;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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


    }
}

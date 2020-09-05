package br.com.tiagocalixto.pokedex.use_case.handler;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.delete.DeleteByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindAllByNameUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindOneByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindOneByNumberUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindPageableUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.persist.PersistUseCase;
import br.com.tiagocalixto.pokedex.use_case.handler.impl.HandlerUseCaseImpl;
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
import java.util.Random;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class HandlerUseCaseTest {

    @InjectMocks
    private HandlerUseCaseImpl handler;

    @Mock
    private PersistUseCase<Pokemon> save;

    @Mock
    private PersistUseCase<Pokemon> update;

    @Mock
    private DeleteByIdUseCase delete;

    @Mock
    private FindOneByIdUseCase<Pokemon> findById;

    @Mock
    private FindAllByNameUseCase<Pokemon> findByName;

    @Mock
    private FindOneByNumberUseCase findByNumber;

    @Mock
    private FindPageableUseCase<Pokemon> findPageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(handler, "save", save);
        ReflectionTestUtils.setField(handler, "update", update);
        ReflectionTestUtils.setField(handler, "delete", delete);
        ReflectionTestUtils.setField(handler, "findById", findById);
        ReflectionTestUtils.setField(handler, "findByName", findByName);
        ReflectionTestUtils.setField(handler, "findByNumber", findByNumber);
        ReflectionTestUtils.setField(handler, "findPageable", findPageable);
    }


    @Test
    @SneakyThrows
    void givenPokemon_whenSavePokemon_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(save.execute(any(Pokemon.class)))
                .thenReturn(pokemon);

        Pokemon result = handler.savePokemon(pokemon);

        assertTrue(result != null);
        assertThat(pokemon.getName()).isEqualTo(result.getName());

        verify(save, times(1))
                .execute(any(Pokemon.class));
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenUpdatePokemon_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(update.execute(any(Pokemon.class)))
                .thenReturn(pokemon);

        Pokemon result = handler.updatePokemon(pokemon);

        assertTrue(result != null);
        assertThat(pokemon.getName()).isEqualTo(result.getName());

        verify(update, times(1))
                .execute(any(Pokemon.class));
    }

    @Test
    @SneakyThrows
    void givenId_whenDeletePokemonById_thenDeletePokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        doNothing().when(delete).execute(anyLong());

        handler.deletePokemonById(pokemon.getId());

        verify(delete, times(1))
                .execute(anyLong());
    }

    @Test
    @SneakyThrows
    void givenId_whenFindPokemonById_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(findById.execute(anyLong()))
                .thenReturn(pokemon);

        Pokemon result = handler.findPokemonById(pokemon.getId());

        assertTrue(result != null);
        assertThat(pokemon.getName()).isEqualTo(result.getName());

        verify(findById, times(1))
                .execute(anyLong());
    }

    @Test
    @SneakyThrows
    void givenName_whenFindPokemonByName_thenReturnPokemonList() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(findByName.execute(anyString()))
                .thenReturn(List.of(pokemon));

        List<Pokemon> result = handler.findPokemonByName(pokemon.getName());

        assertFalse(result.isEmpty());
        assertThat(pokemon.getName()).isEqualTo(result.get(0).getName());

        verify(findByName, times(1))
                .execute(anyString());
    }

    @Test
    @SneakyThrows
    void givenNumber_whenFindPokemonByNumber_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(findByNumber.execute(anyLong()))
                .thenReturn(pokemon);

        Pokemon result = handler.findPokemonByNumber(pokemon.getId());

        assertTrue(result != null);
        assertThat(pokemon.getName()).isEqualTo(result.getName());

        verify(findByNumber, times(1))
                .execute(anyLong());
    }

    @Test
    @SneakyThrows
    void givenPageNumber_whenFindPokemonPageable_thenReturnPokemonPage() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(findPageable.execute(anyInt()))
                .thenReturn(List.of(pokemon));

        List<Pokemon> result = handler.findPokemonPageable(new Random().nextInt());

        assertFalse(result.isEmpty());
        assertThat(pokemon.getName()).isEqualTo(result.get(0).getName());

        verify(findPageable, times(1))
                .execute(anyInt());
    }

}

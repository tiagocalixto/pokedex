package br.com.tiagocalixto.pokedex.use_case.mediator;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.ExistsByNumberUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindOneByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindOneByNumberUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.AssociateOrInsertEvolveToUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.AssociateOrInsertEvolvedFromUseCase;
import br.com.tiagocalixto.pokedex.use_case.mediator.impl.PokemonMediatorUseCaseImpl;
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
public class PokemonMediatorUseCaseTest {

    @InjectMocks
    PokemonMediatorUseCaseImpl mediator;

    @Mock
    private FindOneByIdUseCase<Pokemon> findOneByIdUseCase;

    @Mock
    private AssociateOrInsertEvolvedFromUseCase associateOrInsertEvolvedFrom;

    @Mock
    private AssociateOrInsertEvolveToUseCase associateOrInsertEvolveToEvolveTo;

    @Mock
    private ExistsByNumberUseCase existsByNumberUseCase;

    @Mock
    private FindOneByNumberUseCase findOneByNumberUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(mediator, "findOneByIdUseCase", findOneByIdUseCase);
        ReflectionTestUtils.setField(mediator, "associateOrInsertEvolvedFrom", associateOrInsertEvolvedFrom);
        ReflectionTestUtils.setField(mediator, "associateOrInsertEvolveToEvolveTo", associateOrInsertEvolveToEvolveTo);
        ReflectionTestUtils.setField(mediator, "existsByNumberUseCase", existsByNumberUseCase);
        ReflectionTestUtils.setField(mediator, "findOneByNumberUseCase", findOneByNumberUseCase);
    }


    @Test
    @SneakyThrows
    void givenNumber_whenPokemonExistsByNumber_thenReturnTrue() {

        when(existsByNumberUseCase.execute(anyLong()))
                .thenReturn(true);

        boolean result = mediator.pokemonExistsByNumber(new Random().nextLong());

        assertTrue(result);

        verify(existsByNumberUseCase, times(1))
                .execute(anyLong());
    }

    @Test
    @SneakyThrows
    void givenId_whenPokemonFindById_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(findOneByIdUseCase.execute(anyLong()))
                .thenReturn(pokemon);

        Pokemon result = mediator.pokemonFindById(pokemon.getId());

        assertTrue(result != null);
        assertThat(pokemon.getName()).isEqualTo(result.getName());

        verify(findOneByIdUseCase, times(1))
                .execute(anyLong());
    }

    @Test
    @SneakyThrows
    void givenId_whenPokemonFindByNumber_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(findOneByNumberUseCase.execute(anyLong()))
                .thenReturn(pokemon);

        Pokemon result = mediator.pokemonFindByNumber(pokemon.getNumber());

        assertTrue(result != null);
        assertThat(pokemon.getName()).isEqualTo(result.getName());

        verify(findOneByNumberUseCase, times(1))
                .execute(anyLong());
    }

    @Test
    @SneakyThrows
    void givenId_whenAssociateOrInsertEvolvedFrom_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(associateOrInsertEvolvedFrom.execute(any(Pokemon.class)))
                .thenReturn(pokemon.getEvolvedFrom());

        PokemonEvolution result = mediator.associateOrInsertEvolvedFrom(pokemon);

        assertTrue(result != null);
        assertThat(pokemon.getEvolvedFrom().getPokemon().getName()).isEqualTo(result.getPokemon().getName());

        verify(associateOrInsertEvolvedFrom, times(1))
                .execute(any(Pokemon.class));
    }

    @Test
    @SneakyThrows
    void givenId_whenAssociateOrInsertEvolveTo_thenReturnPokemon() {

        Pokemon pokemon = MocksDomain.createPokemon();

        when(associateOrInsertEvolveToEvolveTo.execute(any(Pokemon.class)))
                .thenReturn(pokemon.getEvolveTo());

        List<PokemonEvolution> result = mediator.associateOrInsertEvolveTo(pokemon);

        assertFalse(result.isEmpty());
        assertThat(pokemon.getEvolveTo().get(0).getPokemon().getName())
                .isEqualTo(result.get(0).getPokemon().getName());

        verify(associateOrInsertEvolveToEvolveTo, times(1))
                .execute(any(Pokemon.class));
    }
}

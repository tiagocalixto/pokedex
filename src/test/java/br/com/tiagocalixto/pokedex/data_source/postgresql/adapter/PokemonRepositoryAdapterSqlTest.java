package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksEntity;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class PokemonRepositoryAdapterSqlTest {

    @InjectMocks
    private PokemonRepositoryAdapterSql adapter;

    @Mock
    private PokemonRepository repository;

    @Mock
    private ConverterEntitySql<PokemonEntity, Pokemon> converter;

    @Mock
    private InsertRepositoryPort<Object> audit;

    @Mock
    private PreparePokemonToPersistSql prepareToPersist;


    @Test
    @SneakyThrows
    void givenPokemon_whenInsertPokemonInSqlPostgresDataBase_thenReturnPokemonSaved() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());

        doNothing().when(audit).insertAsync(any(Object.class));
        when(prepareToPersist.prepareToInsert(any(PokemonEntity.class))).thenReturn(pokemonEntity);
        when(repository.save(any(PokemonEntity.class))).thenReturn(pokemonEntity);
        when(converter.convertToEntityNotOptional(any(Pokemon.class))).thenReturn(pokemonEntity);
        when(converter.convertToDomainNotOptional(any(PokemonEntity.class))).thenReturn(pokemon);

        Pokemon saved = adapter.insert(pokemon);

        assertThat(saved).isEqualTo(pokemon);

        verify(audit, times(1)).insertAsync(any(Object.class));
        verify(prepareToPersist, times(1)).prepareToInsert(any(PokemonEntity.class));
        verify(repository, times(1)).save(any(PokemonEntity.class));
        verify(converter, times(1)).convertToEntityNotOptional(any(Pokemon.class));
        verify(converter, times(1)).convertToDomainNotOptional(any(PokemonEntity.class));
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenUpdatePokemonInSqlPostgresDataBase_thenReturnPokemonUpdated() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());

        doNothing().when(audit).insertAsync(any(Object.class));
        when(prepareToPersist.prepareToUpdate(any(PokemonEntity.class))).thenReturn(pokemonEntity);
        when(repository.save(any(PokemonEntity.class))).thenReturn(pokemonEntity);
        when(converter.convertToEntityNotOptional(any(Pokemon.class))).thenReturn(pokemonEntity);
        when(converter.convertToDomainNotOptional(any(PokemonEntity.class))).thenReturn(pokemon);

        Pokemon updated = adapter.update(pokemon);

        assertThat(updated).isEqualTo(pokemon);

        verify(audit, times(1)).insertAsync(any(Object.class));
        verify(prepareToPersist, times(1)).prepareToUpdate(any(PokemonEntity.class));
        verify(repository, times(1)).save(any(PokemonEntity.class));
        verify(converter, times(1)).convertToEntityNotOptional(any(Pokemon.class));
        verify(converter, times(1)).convertToDomainNotOptional(any(PokemonEntity.class));
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenDeletePokemonInSqlPostgresDataBase_thenDeletePokemon() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());

        doNothing().when(repository).deleteById(anyLong());

        adapter.delete(pokemon);

        verify(repository, times(1)).deleteById(anyLong());
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenFindPokemonByNameInSqlPostgresDataBase_thenReturnPokemonList() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());

        when(repository.findAllByPhoneticName(anyString())).thenReturn(Set.of(pokemonEntity.getId()));
        when(converter.convertToDomainNotOptional(any(PokemonEntity.class))).thenReturn(pokemon);
        when(repository.findById(anyLong())).thenReturn(Optional.of(pokemonEntity));

        List<Pokemon> founded = adapter.findAllByName(pokemon.getName());

        founded.forEach(item -> {
            assertThat(item).isEqualTo(pokemon);
        });

        verify(repository, times(1)).findAllByPhoneticName(anyString());
        verify(repository, times(1)).findById(anyLong());
        verify(converter, times(1)).convertToDomainNotOptional(any(PokemonEntity.class));
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenFindPokemonByNumberInSqlPostgresDataBase_thenReturnPokemon() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());

        when(repository.findFirstByNumber(anyLong())).thenReturn(Optional.of(pokemonEntity));
        when(converter.convertToDomain(Optional.of(pokemonEntity))).thenReturn(Optional.of(pokemon));

        Optional<Pokemon> founded = adapter.findByNumber(pokemon.getNumber());

        founded.ifPresent(item -> assertThat(item).isEqualTo(pokemon));

        verify(repository, times(1)).findFirstByNumber(anyLong());
        verify(converter, times(1)).convertToDomain(Optional.of(pokemonEntity));
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenFindPokemonPageableInSqlPostgresDataBase_thenReturnPokemonList() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());
        Page<PokemonEntity> page = new PageImpl<PokemonEntity>(List.of(pokemonEntity));

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);
        when(converter.convertToDomainList(anyList())).thenReturn(List.of(pokemon));

        List<Pokemon> founded = adapter.findPageable(1, 10, "number");

        founded.forEach(item -> {
            assertThat(item).isEqualTo(pokemon);
        });

        verify(repository, times(1)).findAll(any(PageRequest.class));
        verify(converter, times(1)).convertToDomainList(anyList());
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenFindPokemonByIdInSqlPostgresDataBase_thenReturnPokemon() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        Pokemon pokemon = MocksDomain.createPokemon(pokemonEntity.getId());

        when(repository.findById(anyLong())).thenReturn(Optional.of(pokemonEntity));
        when(converter.convertToDomain(Optional.of(pokemonEntity))).thenReturn(Optional.of(pokemon));

        Optional<Pokemon> founded = adapter.findById(pokemon.getId());

        founded.ifPresent(item -> assertThat(item).isEqualTo(pokemon));

        verify(repository, times(1)).findById(anyLong());
        verify(converter, times(1)).convertToDomain(Optional.of(pokemonEntity));
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenExistsPokemonByIdInSqlPostgresDataBase_thenReturnTrue() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();

        when(repository.existsByNumber(anyLong())).thenReturn(true);

        boolean result = adapter.isExistsByNumber(pokemonEntity.getNumber());

        assertThat(result).isEqualTo(true);

        verify(repository, times(1)).existsByNumber(anyLong());
        resetMock();
    }

    private void resetMock() {

        reset(repository);
        reset(converter);
        reset(audit);
        reset(prepareToPersist);
    }
}

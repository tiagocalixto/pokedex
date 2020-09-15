package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.adapter.PreparePokemonToPersistSql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.*;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionStoneEnum;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
import br.com.tiagocalixto.pokedex.mock.MocksEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PreparePokemonToPersistSqlTest {

    @InjectMocks
    private PreparePokemonToPersistSql prepare;

    @Mock
    private TypeRepository repositoryType;

    @Mock
    private MoveRepository repositoryMove;

    @Mock
    private EvolutionStoneRepository repositoryEvolutionStone;

    @Mock
    private EvolutionTriggerRepository repositoryEvolutionTrigger;

    @Mock
    private AbilityRepository repositoryAbility;

    @Mock
    private PokemonRepository repositoryPokemon;


    @Test
    @SneakyThrows
    void givenPokemon_whenPrepareToInsert_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();

        when(repositoryType.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createType()));
        when(repositoryAbility.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createAbility()));
        when(repositoryMove.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createMove()));
        when(repositoryEvolutionTrigger.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionTrigger()));
        when(repositoryEvolutionStone.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionStone()));
        when(repositoryPokemon.findFirstByNumber(anyLong()))
                .thenReturn(Optional.of(MocksEntity.createEvolvedFromPokemon()));

        PokemonEntity saved = prepare.prepareToInsert(pokemonEntity);

        assertThat(saved.getName()).isEqualTo(pokemonEntity.getName());
        assertThat(saved.getType().get(0).getType().getDescription())
                .isEqualTo(pokemonEntity.getType().get(0).getType().getDescription());

        verify(repositoryType,times(6)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryAbility,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryMove,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionTrigger,times(2))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionStone,times(0))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryPokemon,times(2)).findFirstByNumber(anyLong());
    }

    @Test
    @SneakyThrows
    void givenPokemonWithEvolvedFromUsingStone_whenPrepareToInsert_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        pokemonEntity.getEvolvedFrom().forEach(item -> {
            item.setEvolutionTrigger(EvolutionTriggerEntity.builder()
                    .description(EvolutionTriggerEnum.USE_ITEM.toString())
                    .build());
            item.setLevel(null);
            item.setEvolutionItem(EvolutionStoneEntity.builder()
                    .description(EvolutionStoneEnum.MOON_STONE.toString())
                    .build());
        });

        when(repositoryType.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createType()));
        when(repositoryAbility.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createAbility()));
        when(repositoryMove.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createMove()));
        when(repositoryEvolutionTrigger.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionTrigger()));
        when(repositoryEvolutionStone.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionStone()));
        when(repositoryPokemon.findFirstByNumber(anyLong()))
                .thenReturn(Optional.of(MocksEntity.createEvolvedFromPokemon()));

        PokemonEntity saved = prepare.prepareToInsert(pokemonEntity);

        assertThat(saved.getName()).isEqualTo(pokemonEntity.getName());
        assertThat(saved.getType().get(0).getType().getDescription())
                .isEqualTo(pokemonEntity.getType().get(0).getType().getDescription());

        verify(repositoryType,times(6)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryAbility,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryMove,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionTrigger,times(2))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionStone,times(1))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryPokemon,times(2)).findFirstByNumber(anyLong());
    }

    @Test
    @SneakyThrows
    void givenPokemonWithEntitiesCreatedOnSaveTime_whenPrepareToInsert_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        pokemonEntity.getEvolvedFrom().forEach(item -> {
            item.setEvolutionTrigger(EvolutionTriggerEntity.builder()
                    .description(EvolutionTriggerEnum.USE_ITEM.toString())
                    .build());
            item.setLevel(null);
            item.setEvolutionItem(EvolutionStoneEntity.builder()
                    .description(EvolutionStoneEnum.MOON_STONE.toString())
                    .build());
        });

        when(repositoryType.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryAbility.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryMove.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryEvolutionTrigger.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryEvolutionStone.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryPokemon.findFirstByNumber(anyLong()))
                .thenReturn(Optional.empty());
        when(repositoryType.save(any(TypeEntity.class))).thenReturn(MocksEntity.createType());
        when(repositoryAbility.save(any(AbilityEntity.class))).thenReturn(MocksEntity.createAbility());
        when(repositoryMove.save(any(MoveEntity.class))).thenReturn(MocksEntity.createMove());
        when(repositoryEvolutionTrigger.save(any(EvolutionTriggerEntity.class)))
                .thenReturn(MocksEntity.createEvolutionTrigger());
        when(repositoryEvolutionStone.save(any(EvolutionStoneEntity.class)))
                .thenReturn(MocksEntity.createEvolutionStone());

        PokemonEntity saved = prepare.prepareToInsert(pokemonEntity);

        assertThat(saved.getName()).isEqualTo(pokemonEntity.getName());
        assertThat(saved.getType().get(0).getType().getDescription())
                .isEqualTo(pokemonEntity.getType().get(0).getType().getDescription());

        verify(repositoryType,times(6)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryAbility,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryMove,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionTrigger,times(2))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionStone,times(1))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryPokemon,times(2)).findFirstByNumber(anyLong());
        verify(repositoryType,times(6)).save(any(TypeEntity.class));
        verify(repositoryAbility,times(1)).save(any(AbilityEntity.class));
        verify(repositoryMove,times(1)).save(any(MoveEntity.class));
        verify(repositoryEvolutionTrigger,times(2))
                .save(any(EvolutionTriggerEntity.class));
        verify(repositoryEvolutionStone,times(1))
                .save(any(EvolutionStoneEntity.class));
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenPrepareToUpdate_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();

        when(repositoryType.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createType()));
        when(repositoryAbility.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createAbility()));
        when(repositoryMove.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createMove()));
        when(repositoryEvolutionTrigger.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionTrigger()));
        when(repositoryEvolutionStone.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionStone()));
        when(repositoryPokemon.findFirstByNumber(anyLong()))
                .thenReturn(Optional.of(MocksEntity.createEvolvedFromPokemon()));

        PokemonEntity updated = prepare.prepareToUpdate(pokemonEntity);

        assertThat(updated.getName()).isEqualTo(pokemonEntity.getName());
        assertThat(updated.getType().get(0).getType().getDescription())
                .isEqualTo(pokemonEntity.getType().get(0).getType().getDescription());

        verify(repositoryType,times(6)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryAbility,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryMove,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionTrigger,times(2))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionStone,times(0))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryPokemon,times(2)).findFirstByNumber(anyLong());
    }

    @Test
    @SneakyThrows
    void givenPokemonWithEvolvedFromUsingStone_whenPrepareToUpdate_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        pokemonEntity.getEvolvedFrom().forEach(item -> {
            item.setEvolutionTrigger(EvolutionTriggerEntity.builder()
                    .description(EvolutionTriggerEnum.USE_ITEM.toString())
                    .build());
            item.setLevel(null);
            item.setEvolutionItem(EvolutionStoneEntity.builder()
                    .description(EvolutionStoneEnum.MOON_STONE.toString())
                    .build());
        });

        when(repositoryType.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createType()));
        when(repositoryAbility.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createAbility()));
        when(repositoryMove.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createMove()));
        when(repositoryEvolutionTrigger.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionTrigger()));
        when(repositoryEvolutionStone.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.of(MocksEntity.createEvolutionStone()));
        when(repositoryPokemon.findFirstByNumber(anyLong()))
                .thenReturn(Optional.of(MocksEntity.createEvolvedFromPokemon()));

        PokemonEntity updated = prepare.prepareToUpdate(pokemonEntity);

        assertThat(updated.getName()).isEqualTo(pokemonEntity.getName());
        assertThat(updated.getType().get(0).getType().getDescription())
                .isEqualTo(pokemonEntity.getType().get(0).getType().getDescription());

        verify(repositoryType,times(6)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryAbility,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryMove,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionTrigger,times(2))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionStone,times(1))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryPokemon,times(2)).findFirstByNumber(anyLong());
    }

    @Test
    @SneakyThrows
    void givenPokemonWithEntitiesCreatedOnSaveTime_whenPrepareToUpdate_thenReturnPokemonEntity() {

        PokemonEntity pokemonEntity = MocksEntity.createPokemon();
        pokemonEntity.getEvolvedFrom().forEach(item -> {
            item.setEvolutionTrigger(EvolutionTriggerEntity.builder()
                    .description(EvolutionTriggerEnum.USE_ITEM.toString())
                    .build());
            item.setLevel(null);
            item.setEvolutionItem(EvolutionStoneEntity.builder()
                    .description(EvolutionStoneEnum.MOON_STONE.toString())
                    .build());
        });

        when(repositoryType.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryAbility.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryMove.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryEvolutionTrigger.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryEvolutionStone.findFirstByDescriptionIgnoringCase(anyString()))
                .thenReturn(Optional.empty());
        when(repositoryPokemon.findFirstByNumber(anyLong()))
                .thenReturn(Optional.empty());
        when(repositoryType.save(any(TypeEntity.class))).thenReturn(MocksEntity.createType());
        when(repositoryAbility.save(any(AbilityEntity.class))).thenReturn(MocksEntity.createAbility());
        when(repositoryMove.save(any(MoveEntity.class))).thenReturn(MocksEntity.createMove());
        when(repositoryEvolutionTrigger.save(any(EvolutionTriggerEntity.class)))
                .thenReturn(MocksEntity.createEvolutionTrigger());
        when(repositoryEvolutionStone.save(any(EvolutionStoneEntity.class)))
                .thenReturn(MocksEntity.createEvolutionStone());

        PokemonEntity update = prepare.prepareToUpdate(pokemonEntity);

        assertThat(update.getName()).isEqualTo(pokemonEntity.getName());
        assertThat(update.getType().get(0).getType().getDescription())
                .isEqualTo(pokemonEntity.getType().get(0).getType().getDescription());

        verify(repositoryType,times(6)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryAbility,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryMove,times(1)).findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionTrigger,times(2))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryEvolutionStone,times(1))
                .findFirstByDescriptionIgnoringCase(anyString());
        verify(repositoryPokemon,times(2)).findFirstByNumber(anyLong());
        verify(repositoryType,times(6)).save(any(TypeEntity.class));
        verify(repositoryAbility,times(1)).save(any(AbilityEntity.class));
        verify(repositoryMove,times(1)).save(any(MoveEntity.class));
        verify(repositoryEvolutionTrigger,times(2))
                .save(any(EvolutionTriggerEntity.class));
        verify(repositoryEvolutionStone,times(1))
                .save(any(EvolutionStoneEntity.class));
    }
}

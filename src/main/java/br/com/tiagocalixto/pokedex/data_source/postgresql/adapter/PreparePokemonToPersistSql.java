package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component("PreparePokemonToPersistSql")
public class PreparePokemonToPersistSql {

    //<editor-fold: properties>
    private TypeRepository repositoryType;
    private MoveRepository repositoryMove;
    private EvolutionStoneRepository repositoryEvolutionStone;
    private EvolutionTriggerRepository repositoryEvolutionTrigger;
    private AbilityRepository repositoryAbility;
    private PokemonRepository repositoryPokemon;
    //</editor-fold>

    //<editor-fold: constructor>
    public PreparePokemonToPersistSql(TypeRepository repositoryType,
                                      MoveRepository repositoryMove,
                                      EvolutionStoneRepository repositoryEvolutionStone,
                                      EvolutionTriggerRepository repositoryEvolutionTrigger,
                                      AbilityRepository repositoryAbility, PokemonRepository repositoryPokemon) {

        this.repositoryType = repositoryType;
        this.repositoryMove = repositoryMove;
        this.repositoryEvolutionStone = repositoryEvolutionStone;
        this.repositoryEvolutionTrigger = repositoryEvolutionTrigger;
        this.repositoryAbility = repositoryAbility;
        this.repositoryPokemon = repositoryPokemon;
    }
    //</editor-fold>


    public PokemonEntity prepareToInsert(PokemonEntity insert) {

        log.info("prepare pokemon to insert {}", insert.getName());

        insert.setId(null);
        preparePokemon(insert);
        log.info("pokemon ready to insert {}", insert.getName());

        return insert;
    }

    @SuppressWarnings("Duplicates")
    public PokemonEntity prepareToUpdate(PokemonEntity update) {

        log.info("prepare pokemon to update {}", update.getName());
        preparePokemon(update);
        log.info("pokemon ready to update {}", update.getName());

        return update;
    }

    private void preparePokemon(PokemonEntity pokemon) {

        log.info("prepare pokemon - {}", pokemon.getName());

        pokemon.getType().forEach(item -> item.setType(getAttachedTypeEntity(item.getType())));
        pokemon.getAbility().forEach(item -> item.setAbility(getAttachedAbilityEntity(item.getAbility())));
        pokemon.getWeakness().forEach(item -> item.setType(getAttachedTypeEntity(item.getType())));

        pokemon.getMove().forEach(item -> {
            item.getMove().setType(getAttachedTypeEntity(item.getMove().getType()));
            item.setMove(getAttachedMoveEntity(item.getMove()));
        });

        if (pokemon.getEvolvedFrom() != null) {

            log.info("Prepare pokemon evolved from - {} ", pokemon.getEvolvedFrom().getPokemon().getName());

            pokemon.getEvolvedFrom().setEvolutionTrigger(
                    getAttachedEvolutionTrigger(pokemon.getEvolvedFrom().getEvolutionTrigger()));

            pokemon.getEvolvedFrom().setEvolutionItem(
                    getAttachedEvolutionStoneEntity(pokemon.getEvolvedFrom().getEvolutionItem()));

            preparePokemon(pokemon.getEvolvedFrom().getPokemon());

            pokemon.getEvolvedFrom().setPokemon(getAttachedPokemonEntity(pokemon.getEvolvedFrom().getPokemon()));

            log.info("Pokemon evolved from attached successfully");
        }

        if (!pokemon.getEvolveTo().isEmpty()) {
            pokemon.getEvolveTo().forEach(item -> {
                log.info("Prepare pokemon evolve to  - {} ", item.getEvolution().getName());

                item.setEvolutionTrigger(getAttachedEvolutionTrigger(item.getEvolutionTrigger()));
                item.setEvolutionItem(getAttachedEvolutionStoneEntity(item.getEvolutionItem()));
                preparePokemon(item.getEvolution());
                item.setEvolution(getAttachedPokemonEntity(item.getEvolution()));

                log.info("Prepare pokemon evolve to  attached successfully");
            });
        }
    }

    private TypeEntity getAttachedTypeEntity(TypeEntity typeEntity) {

        log.info("Getting attached typeEntity");

        if (typeEntity == null) {
            log.info("TypeEntity is null, will not be treated!");
            return null;
        }

        return repositoryType.findFirstByDescriptionIgnoringCase(typeEntity.getDescription())
                .orElseGet(() -> repositoryType.save(typeEntity));
    }

    private MoveEntity getAttachedMoveEntity(MoveEntity moveEntity) {

        log.info("Getting attached moveEntity");

        if (moveEntity == null) {
            log.info("MoveEntity is null, will not be treated!");
            return null;
        }

        return repositoryMove.findFirstByDescriptionIgnoringCase(moveEntity.getDescription())
                .orElseGet(() -> repositoryMove.save(moveEntity));
    }

    private AbilityEntity getAttachedAbilityEntity(AbilityEntity abilityEntity) {

        log.info("Getting attached abilityEntity");

        if (abilityEntity == null) {
            log.info("AbilityEntity is null, will not be treated!");
            return null;
        }

        return repositoryAbility.findFirstByDescriptionIgnoringCase(abilityEntity.getDescription())
                .orElseGet(() -> repositoryAbility.save(abilityEntity));
    }

    private EvolutionStoneEntity getAttachedEvolutionStoneEntity(EvolutionStoneEntity evolutionStoneEntity) {

        log.info("Getting attached evolution stone");

        if (evolutionStoneEntity == null || evolutionStoneEntity.getDescription() == null) {
            log.info("evolution stone is null, will not be treated!");
            return null;
        }

        return repositoryEvolutionStone
                .findFirstByDescriptionIgnoringCase(evolutionStoneEntity.getDescription())
                .orElseGet(() -> repositoryEvolutionStone.save(evolutionStoneEntity));
    }

    private EvolutionTriggerEntity getAttachedEvolutionTrigger(EvolutionTriggerEntity evolutionTriggerEntity) {

        log.info("Getting attached evolution trigger");

        if (evolutionTriggerEntity == null) {
            log.info("evolution trigger is null, will not be treated!");
            return null;
        }

        return repositoryEvolutionTrigger
                .findFirstByDescriptionIgnoringCase(evolutionTriggerEntity.getDescription())
                .orElseGet(() -> repositoryEvolutionTrigger.save(evolutionTriggerEntity));
    }

    @Caching(evict = {
            @CacheEvict(value = "PokemonSqlId", key = "#pokemon.id", condition = "#pokemon.id != null"),
            @CacheEvict(value = "PokemonSqlNumber", key = "#pokemon.number")
    })
    protected PokemonEntity getAttachedPokemonEntity(PokemonEntity pokemon) {

        log.info("Getting attached pokemon evolution - {}", pokemon.getName());

        if (pokemon == null) {
            log.info("pokemon is null, will not be treated!");
            return null;
        }

        return repositoryPokemon.findFirstByNumber(pokemon.getNumber())
                .orElse(pokemon);
    }
}
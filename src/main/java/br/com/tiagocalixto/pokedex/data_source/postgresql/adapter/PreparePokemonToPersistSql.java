package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("PreparePokemonToPersistSql")
public class PreparePokemonToPersistSql {

    //<editor-fold: properties>
    private TypeRepository repositoryType;
    private MoveRepository repositoryMove;
    private EvolutionStoneRepository repositoryEvolutionStone;
    private EvolutionTriggerRepository repositoryEvolutionTrigger;
    private AbilityRepository repositoryAbility;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PreparePokemonToPersistSql(TypeRepository repositoryType,
                                      MoveRepository repositoryMove,
                                      EvolutionStoneRepository repositoryEvolutionStone,
                                      EvolutionTriggerRepository repositoryEvolutionTrigger,
                                      AbilityRepository repositoryAbility) {

        this.repositoryType = repositoryType;
        this.repositoryMove = repositoryMove;
        this.repositoryEvolutionStone = repositoryEvolutionStone;
        this.repositoryEvolutionTrigger = repositoryEvolutionTrigger;
        this.repositoryAbility = repositoryAbility;
    }
    //</editor-fold>


    public PokemonEntity prepareToInsert(PokemonEntity insert) {

        insert.setId(null);
        preparePokemon(insert);

        return insert;
    }

    @SuppressWarnings("Duplicates")
    public PokemonEntity prepareToUpdate(PokemonEntity update) {

        preparePokemon(update);
        setEmbeddedId(update);

        return update;
    }

    private void preparePokemon(PokemonEntity pokemon) {

        pokemon.getType().forEach(item -> item.setType(prepareTypeEntity(item.getType())));

        if (pokemon.getEvolvedFrom() != null) {
            pokemon.getEvolvedFrom().setEvolutionTrigger(
                    prepareEvolutionTriggerEntity(pokemon.getEvolvedFrom().getEvolutionTrigger()));

            pokemon.getEvolvedFrom().setEvolutionItem(
                    prepareEvolutionStoneEntity(pokemon.getEvolvedFrom().getEvolutionItem()));
        }

        pokemon.getEvolveTo().forEach(item -> {
            item.setEvolutionTrigger(item.getEvolutionTrigger());
            item.setEvolutionItem(item.getEvolutionItem());
        });

        pokemon.getMove().forEach(item -> {
            item.getMove().setType(prepareTypeEntity(item.getMove().getType()));
            item.setMove(prepareMoveEntity(item.getMove()));
        });

        pokemon.getAbility().forEach(item -> item.setAbility(prepareAbilityEntity(item.getAbility())));
        pokemon.getWeakness().forEach(item -> item.setType(item.getType()));
    }

    private TypeEntity prepareTypeEntity(TypeEntity type) {

        return repositoryType.findFirstByDescriptionIgnoreCaseAndIgnoreAccents(type.getDescription())
                .orElseGet(() -> repositoryType.save(type));
    }

    private MoveEntity prepareMoveEntity(MoveEntity move) {

        return repositoryMove.findFirstByDescriptionIgnoreCaseAndIgnoreAccents(move.getDescription())
                .orElseGet(() -> repositoryMove.save(move));
    }

    private EvolutionStoneEntity prepareEvolutionStoneEntity(EvolutionStoneEntity evolutionStoneEntity) {

        if (evolutionStoneEntity == null || evolutionStoneEntity.getDescription() == null) {
            return null;
        }

        return repositoryEvolutionStone
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(evolutionStoneEntity.getDescription())
                .orElseGet(() -> repositoryEvolutionStone.save(evolutionStoneEntity));
    }

    private EvolutionTriggerEntity prepareEvolutionTriggerEntity(EvolutionTriggerEntity evolutionTriggerEntity) {

        return repositoryEvolutionTrigger
                .findFirstByDescriptionIgnoreCaseAndIgnoreAccents(evolutionTriggerEntity.getDescription())
                .orElseGet(() -> repositoryEvolutionTrigger.save(evolutionTriggerEntity));
    }

    private AbilityEntity prepareAbilityEntity(AbilityEntity abilityEntity) {

        return repositoryAbility.findFirstByDescriptionIgnoreCaseAndIgnoreAccents(abilityEntity.getDescription())
                .orElseGet(() -> repositoryAbility.save(abilityEntity));
    }

    private void setEmbeddedId(PokemonEntity pokemon) {

        pokemon.getType().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdTypeFk(item.getType().getId()));

        pokemon.getAbility().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdAbilityFk(item.getAbility().getId()));

        pokemon.getMove().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdMoveFk(item.getMove().getId()));

        pokemon.getWeakness().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdTypeFk(item.getType().getId()));

        pokemon.getEvolveTo().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdEvolutionFk(item.getEvolution().getId()));
    }
}

package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_NOT_FOUND;

@Component("PreparePokemonToPersistSql")
public class PreparePokemonToPersistSql {

    //<editor-fold: properties>
    private TypeRepository repositoryType;
    private PokemonRepository repositoryPokemon;
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
                                      AbilityRepository repositoryAbility,
                                      PokemonRepository repositoryPokemon) {

        this.repositoryType = repositoryType;
        this.repositoryMove = repositoryMove;
        this.repositoryEvolutionStone = repositoryEvolutionStone;
        this.repositoryEvolutionTrigger = repositoryEvolutionTrigger;
        this.repositoryAbility = repositoryAbility;
        this.repositoryPokemon = repositoryPokemon;
    }
    //</editor-fold>


    public PokemonEntity prepareToInsert(PokemonEntity insert) {

            insert.setId(0L);
            preparePokemon(insert);

            return insert;
    }

    public PokemonEntity prepareToUpdate(PokemonEntity update){

        PokemonEntity pokemonDb = repositoryPokemon.findById(update.getId())
                .orElseThrow(() ->  new EntityNotFoundException(POKEMON_NOT_FOUND));

        preparePokemon(update);

        //de para maravilhoso

    }

    private void preparePokemon(PokemonEntity pokemon){

        pokemon.getType().forEach(item -> item.setType(prepareTypeEntity(item.getType())));
        pokemon.getEvolvedFrom().setEvolutionTrigger(prepareEvolutionTriggerEntity(pokemon.getEvolvedFrom().getEvolutionTrigger()));
        pokemon.getEvolvedFrom().setEvolutionItem(prepareEvolutionStoneEntity(pokemon.getEvolvedFrom().getEvolutionItem()));

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

        if (evolutionStoneEntity == null || evolutionStoneEntity.getDescription() == null){
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

    private AbilityEntity prepareAbilityEntity(AbilityEntity abilityEntity){

        return repositoryAbility.findFirstByDescriptionIgnoreCaseAndIgnoreAccents(abilityEntity.getDescription())
                .orElseGet(() -> repositoryAbility.save(abilityEntity));
    }
}

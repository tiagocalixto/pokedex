package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.*;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Component("PreparePokemonToPersistSql")
public class PreparePokemonToPersistSql {

    //<editor-fold: properties>
    private TypeRepository repositoryType;
    private MoveRepository repositoryMove;
    private EvolutionStoneRepository repositoryEvolutionStone;
    private EvolutionTriggerRepository repositoryEvolutionTrigger;
    private AbilityRepository repositoryAbility;
    private PokemonRepository pokemonRepository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PreparePokemonToPersistSql(TypeRepository repositoryType,
                                      MoveRepository repositoryMove,
                                      EvolutionStoneRepository repositoryEvolutionStone,
                                      EvolutionTriggerRepository repositoryEvolutionTrigger,
                                      AbilityRepository repositoryAbility, PokemonRepository pokemonRepository) {

        this.repositoryType = repositoryType;
        this.repositoryMove = repositoryMove;
        this.repositoryEvolutionStone = repositoryEvolutionStone;
        this.repositoryEvolutionTrigger = repositoryEvolutionTrigger;
        this.repositoryAbility = repositoryAbility;
        this.pokemonRepository = pokemonRepository;
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
        pokemon.getAbility().forEach(item -> item.setAbility(prepareAbilityEntity(item.getAbility())));
        pokemon.getWeakness().forEach(item ->  item.setType(prepareTypeEntity(item.getType())));

        pokemon.getMove().forEach(item -> {
            item.getMove().setType(prepareTypeEntity(item.getMove().getType()));
            item.setMove(prepareMoveEntity(item.getMove()));
        });

        if (pokemon.getEvolvedFrom() != null) {
            pokemon.getEvolvedFrom().setEvolutionTrigger(
                    prepareEvolutionTriggerEntity(pokemon.getEvolvedFrom().getEvolutionTrigger()));

            pokemon.getEvolvedFrom().setPokemon(setPokemonEvolution(pokemon.getEvolvedFrom().getPokemon().getId()));

            pokemon.getEvolvedFrom().setEvolutionItem(
                    prepareEvolutionStoneEntity(pokemon.getEvolvedFrom().getEvolutionItem()));
        }

        pokemon.getEvolveTo().forEach(item -> {
            item.setEvolutionTrigger(prepareEvolutionTriggerEntity(item.getEvolutionTrigger()));
            item.setEvolution(setPokemonEvolution(item.getEvolution().getId()));
            item.setEvolutionItem(prepareEvolutionStoneEntity(item.getEvolutionItem()));
        });

    }

    private TypeEntity prepareTypeEntity(TypeEntity type) {

        Optional<TypeEntity> tttt = repositoryType.findFirstByDescriptionIgnoringCase(type.getDescription());

        if(tttt.isEmpty()){

            TypeEntity ttt = repositoryType.save(type);
        }

        System.out.println("type: " + type.getDescription());
        return repositoryType.findFirstByDescriptionIgnoringCase(type.getDescription())
                .orElseGet(() -> repositoryType.save(type));
    }

    private MoveEntity prepareMoveEntity(MoveEntity move) {

        System.out.println("move: " + move.getDescription());
        return repositoryMove.findFirstByDescriptionIgnoringCase(move.getDescription())
                .orElseGet(() -> repositoryMove.save(move));
    }

    private EvolutionStoneEntity prepareEvolutionStoneEntity(EvolutionStoneEntity evolutionStoneEntity) {

       // System.out.println("evolutionStoneEntity: " + evolutionStoneEntity.getDescription());
        if (evolutionStoneEntity == null || evolutionStoneEntity.getDescription() == null) {
            return null;
        }

        return repositoryEvolutionStone
                .findFirstByDescriptionIgnoringCase(evolutionStoneEntity.getDescription())
                .orElseGet(() -> repositoryEvolutionStone.save(evolutionStoneEntity));
    }

    private EvolutionTriggerEntity prepareEvolutionTriggerEntity(EvolutionTriggerEntity evolutionTriggerEntity) {

        System.out.println("evolutionTriggerEntity: " + evolutionTriggerEntity.getDescription());

        return repositoryEvolutionTrigger
                .findFirstByDescriptionIgnoringCase(evolutionTriggerEntity.getDescription())
                .orElseGet(() -> repositoryEvolutionTrigger.save(evolutionTriggerEntity));
    }

    private AbilityEntity prepareAbilityEntity(AbilityEntity abilityEntity) {

        System.out.println("abilityEntity: " + abilityEntity.getDescription());

        return repositoryAbility.findFirstByDescriptionIgnoringCase(abilityEntity.getDescription())
                .orElseGet(() -> repositoryAbility.save(abilityEntity));
    }

    private void setEmbeddedId(PokemonEntity pokemon) {

        System.out.println("embeded: 1" );
        pokemon.getType().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdTypeFk(item.getType().getId()));
        System.out.println("embeded: 2" );
        pokemon.getAbility().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdAbilityFk(item.getAbility().getId()));
        System.out.println("embeded: 3" );
        pokemon.getMove().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdMoveFk(item.getMove().getId()));
        System.out.println("embeded: 4" );
        pokemon.getWeakness().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdTypeFk(item.getType().getId()));
        System.out.println("embeded: 5" );
        pokemon.getEvolveTo().stream().filter(Objects::nonNull)
                .forEach(item -> item.getId().setIdEvolutionFk(item.getEvolution().getId()));
        System.out.println("embeded: FIM" );
    }

    private PokemonEntity setPokemonEvolution(Long id){

        System.out.println("evolution mother fucker: FIM" + id);
        PokemonEntity pokemon = pokemonRepository.findById(id).orElse(null);

        return pokemon;
    }
}

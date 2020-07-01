package br.com.tiagocalixto.pokedex.use_case.mediator.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.use_case.pokemon_evolution.AssociateOrInsertEvolveToUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon_evolution.AssociateOrInsertEvolvedFromUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.ExistsByNumberUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindOneByNumberUseCase;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.delete.DeleteByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindAllByNameUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindOneByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindPageableUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.persist.PersistUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Component
public class PokemonMediatorUseCaseImpl implements PokemonMediatorUseCase {

    //<editor-fold: properties>
    private DeleteByIdUseCase deleteUseCase;
    private FindAllByNameUseCase<Pokemon> findAllByNameUseCase;
    private FindOneByIdUseCase<Pokemon> findOneByIdUseCase;
    private FindPageableUseCase<Pokemon> findPageableUseCase;
    private PersistUseCase<Pokemon> saveUseCase;
    private PersistUseCase<Pokemon> updateUseCase;
    private AssociateOrInsertEvolvedFromUseCase associateOrInsertEvolvedFrom;
    private AssociateOrInsertEvolveToUseCase associateOrInsertEvolveToEvolveTo;
    private ExistsByNumberUseCase existsByNumberUseCase;
    private FindOneByNumberUseCase findOneByNumberUseCase;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonMediatorUseCaseImpl(DeleteByIdUseCase deleteUseCase, FindAllByNameUseCase<Pokemon> findAllByNameUseCase,
                                      FindOneByIdUseCase<Pokemon> findOneByIdUseCase,
                                      FindPageableUseCase<Pokemon> findPageableUseCase,
                                      @Qualifier(POKEMON_SAVE_USE_CASE) PersistUseCase<Pokemon> saveUseCase,
                                      @Qualifier(POKEMON_UPDATE_USE_CASE) PersistUseCase<Pokemon> updateUseCase,
                                      AssociateOrInsertEvolvedFromUseCase associateOrInsertEvolvedFrom,
                                      AssociateOrInsertEvolveToUseCase associateOrInsertEvolveToEvolveTo,
                                      ExistsByNumberUseCase existsByNumberUseCase,
                                      FindOneByNumberUseCase findOneByNumberUseCase) {

        this.deleteUseCase = deleteUseCase;
        this.findAllByNameUseCase = findAllByNameUseCase;
        this.findOneByIdUseCase = findOneByIdUseCase;
        this.findPageableUseCase = findPageableUseCase;
        this.saveUseCase = saveUseCase;
        this.updateUseCase = updateUseCase;
        this.associateOrInsertEvolvedFrom = associateOrInsertEvolvedFrom;
        this.associateOrInsertEvolveToEvolveTo = associateOrInsertEvolveToEvolveTo;
        this.existsByNumberUseCase = existsByNumberUseCase;
        this.findOneByNumberUseCase = findOneByNumberUseCase;
    }
    //</editor-fold>


    @Override
    public void pokemonDeleteById(Long id) {

        this.deleteUseCase.execute(id);
    }

    @Override
    public boolean pokemonExistsByNumber(Long number) {

        return this.existsByNumberUseCase.execute(number);
    }

    @Override
    public List<Pokemon> pokemonFindAllByName(String name) {

        return this.findAllByNameUseCase.execute(name);
    }

    @Override
    public Pokemon pokemonFindById(Long id) {

        return this.findOneByIdUseCase.execute(id);
    }

    @Override
    public Pokemon pokemonFindByNumber(Long number) {

        return this.findOneByNumberUseCase.execute(number);
    }

    @Override
    public List<Pokemon> pokemonFindPageable(int pageNumber) {

        return this.findPageableUseCase.execute(pageNumber);
    }

    @Override
    public Pokemon save(Pokemon pokemon) {

        return this.saveUseCase.execute(pokemon);
    }

    @Override
    public Pokemon update(Pokemon pokemon) {

        return this.updateUseCase.execute(pokemon);
    }

    @Override
    public PokemonEvolution associateOrInsertEvolvedFrom(Pokemon pokemon) {

        return this.associateOrInsertEvolvedFrom.execute(pokemon);
    }

    @Override
    public List<PokemonEvolution> associateOrInsertEvolveTo(Pokemon pokemon) {

        return this.associateOrInsertEvolveToEvolveTo.execute(pokemon);
    }
}

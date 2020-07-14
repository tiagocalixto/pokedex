package br.com.tiagocalixto.pokedex.use_case.handler.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.use_case.handler.HandlerUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.delete.DeleteByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindAllByNameUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindOneByIdUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindOneByNumberUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.find.FindPageableUseCase;
import br.com.tiagocalixto.pokedex.use_case.pokemon.persist.PersistUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_SAVE_USE_CASE;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_UPDATE_USE_CASE;

@Component
public class HandlerUseCaseImpl implements HandlerUseCase {

    //<editor-fold: properties>
    private PersistUseCase<Pokemon> save;
    private PersistUseCase<Pokemon> update;
    private DeleteByIdUseCase delete;
    private FindOneByIdUseCase<Pokemon> findById;
    private FindAllByNameUseCase<Pokemon> findByName;
    private FindOneByNumberUseCase findByNUmber;
    private FindPageableUseCase<Pokemon> findPageable;
    //</editor-fold>

    //<editor-fold: constructor>
    public HandlerUseCaseImpl(@Qualifier(POKEMON_SAVE_USE_CASE) PersistUseCase<Pokemon> save,
                              @Qualifier(POKEMON_UPDATE_USE_CASE) PersistUseCase<Pokemon> update,
                              DeleteByIdUseCase delete, FindOneByIdUseCase<Pokemon> findById,
                              FindAllByNameUseCase<Pokemon> findByName,
                              FindOneByNumberUseCase findByNUmber,
                              FindPageableUseCase<Pokemon> findPageable) {
        this.save = save;
        this.update = update;
        this.delete = delete;
        this.findById = findById;
        this.findByName = findByName;
        this.findByNUmber = findByNUmber;
        this.findPageable = findPageable;
    }
    //</editor-fold>


    @Override
    public Pokemon savePokemon(Pokemon pokemon) {

        return save.execute(pokemon);
    }

    @Override
    public Pokemon updatePokemon(Pokemon pokemon) {

        return update.execute(pokemon);
    }

    @Override
    public void deletePokemonById(Long id) {

        delete.execute(id);
    }

    @Override
    public Pokemon findPokemonById(Long id) {

        return findById.execute(id);
    }

    @Override
    public List<Pokemon> findPokemonByName(String name) {

        return findByName.execute(name);
    }

    @Override
    public Pokemon findPokemonByNumber(Long number) {

        return findByNUmber.execute(number);
    }

    @Override
    public List<Pokemon> findPokemonPageable(int pageNumber) {

        return findPageable.execute(pageNumber);
    }
}
package br.com.tiagocalixto.pokedex.use_case.mediator.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.*;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.AssociateOrInsertEvolveToUseCase;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon_evolution.AssociateOrInsertEvolvedFromUseCase;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PokemonMediatorUseCaseImpl implements PokemonMediatorUseCase {

    //<editor-fold: properties>
    private FindOneByIdUseCase<Pokemon> findOneByIdUseCase;
    private AssociateOrInsertEvolvedFromUseCase associateOrInsertEvolvedFrom;
    private AssociateOrInsertEvolveToUseCase associateOrInsertEvolveToEvolveTo;
    private ExistsByNumberUseCase existsByNumberUseCase;
    private FindOneByNumberUseCase findOneByNumberUseCase;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonMediatorUseCaseImpl(FindOneByIdUseCase<Pokemon> findOneByIdUseCase,
                                      AssociateOrInsertEvolvedFromUseCase associateOrInsertEvolvedFrom,
                                      AssociateOrInsertEvolveToUseCase associateOrInsertEvolveToEvolveTo,
                                      ExistsByNumberUseCase existsByNumberUseCase,
                                      FindOneByNumberUseCase findOneByNumberUseCase) {

        this.findOneByIdUseCase = findOneByIdUseCase;
        this.associateOrInsertEvolvedFrom = associateOrInsertEvolvedFrom;
        this.associateOrInsertEvolveToEvolveTo = associateOrInsertEvolveToEvolveTo;
        this.existsByNumberUseCase = existsByNumberUseCase;
        this.findOneByNumberUseCase = findOneByNumberUseCase;
    }
    //</editor-fold>


    @Override
    public boolean pokemonExistsByNumber(Long number) {

        return this.existsByNumberUseCase.execute(number);
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
    public PokemonEvolution associateOrInsertEvolvedFrom(Pokemon pokemon) {

        return this.associateOrInsertEvolvedFrom.execute(pokemon);
    }

    @Override
    public List<PokemonEvolution> associateOrInsertEvolveTo(Pokemon pokemon) {

        return this.associateOrInsertEvolveToEvolveTo.execute(pokemon);
    }
}

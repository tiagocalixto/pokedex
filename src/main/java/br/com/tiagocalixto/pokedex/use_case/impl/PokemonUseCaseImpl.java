package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.infra.exception.*;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.*;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Service("PokemonUseCaseImpl")
public class PokemonUseCaseImpl implements PersistUseCase<Pokemon>, UpdateUseCase<Pokemon>, DeleteByIdUseCase<Long>,
        ExistsByIdUseCase, FindPageableUseCase<Pokemon>, FindAllByNameUseCase<Pokemon>, FindOneByIdUseCase<Pokemon> {

    //<editor-fold: properties>
    private FindAllByNameRepositoryPort<Pokemon> findByNameRepository;
    private ExistsByIdRepositoryPort isExistsRepository;
    private InsertRepositoryPort<Pokemon> insertRepository;
    private UpdateRepositoryPort<Pokemon> updateRepository;
    private DeleteRepositoryPort<Pokemon> deleteRepository;
    private EvolutionUseCase evolutionUseCase;
    private FindOneByIdIntegrationPort<Pokemon> nationalDex;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonUseCaseImpl(
                              @Qualifier("PokemonRepositorySql") FindAllByNameRepositoryPort<Pokemon> findByNameRepository,
                              @Qualifier("PokemonRepositorySql") ExistsByIdRepositoryPort isExistsRepository,
                              @Qualifier("PokemonRepositorySql") InsertRepositoryPort<Pokemon> insertRepository,
                              @Qualifier("PokemonRepositorySql") UpdateRepositoryPort<Pokemon> updateRepository,
                              @Qualifier("PokemonRepositorySql") DeleteRepositoryPort<Pokemon> deleteRepository,
                              @Qualifier("EvolutionUseCaseImpl") EvolutionUseCase evolutionUseCase,
                              @Qualifier("NationalDex") FindOneByIdIntegrationPort<Pokemon> nationalDex) {

        this.findByNameRepository = findByNameRepository;
        this.isExistsRepository = isExistsRepository;
        this.insertRepository = insertRepository;
        this.updateRepository = updateRepository;
        this.deleteRepository = deleteRepository;
        this.evolutionUseCase = evolutionUseCase;
        this.nationalDex = nationalDex;
    }
    //</editor-fold>





    @Override
    public boolean execute(Long number) {

        return isExistsRepository.isExistsById(number);
    }

    @Transactional
    @Override
    public Pokemon execute(Pokemon pokemon) {

        verifyPokemonInfo(pokemon);
        return insertRepository.insert(pokemon);
    }

    @Transactional
    @Override
    public Pokemon update(Pokemon pokemon) {

        if(!this.execute(pokemon.getNumber())){
            throw new EntityNotFoundException(POKEMON_NOT_FOUND + pokemon.getNumber());
        }

        verifyPokemonInfo(pokemon);
        return updateRepository.update(pokemon);
    }

    @Transactional
    @Override
    public void execute(Long number) {

        Pokemon pokemon = this.execute(number);
        deleteRepository.delete(pokemon);
    }

    private void verifyPokemonInfo(Pokemon pokemon) {

        Pokemon pokemonNationalDex = nationalDex.findById(pokemon.getNumber())
                .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

        verifyName(pokemon, pokemonNationalDex);
        verifyType(pokemon, pokemonNationalDex);
        verifyMove(pokemon, pokemonNationalDex);
        pokemon.setEvolvedFrom(evolutionUseCase.verifyEvolvedFrom(pokemon));
        pokemon.setEvolveTo(evolutionUseCase.verifyEvolveTo(pokemon));
    }

    private void verifyName(Pokemon pokemon, Pokemon pokemonNationalDex) {

        if (!Util.phoneticStringsMatches(pokemon.getName(), pokemonNationalDex.getName())) {
            throw new PokemonNameIncorrectException(POKEMON_INCORRECT_NAME);
        }
    }

    private void verifyType(Pokemon pokemon, Pokemon pokemonNationalDex) {

        List<Type> dontBelongs = pokemon.getType().stream()
                .filter(item -> !pokemonNationalDex.getType().stream()
                        .map(Type::getDescription)
                        .collect(Collectors.toList()).contains(item.getDescription()))
                .collect(Collectors.toList());

        if (!dontBelongs.isEmpty()) {
            throw new PokemonIncorrectTypeException(POKEMON_INCORRECT_TYPE + " - (" +
                    dontBelongs.stream().map(item -> item.getDescription() + " ").toString() + ")");
        }
    }

    private void verifyMove(Pokemon pokemon, Pokemon pokemonNationalDex) {

        if (!pokemon.getMove().isEmpty()) {

            List<PokemonMove> dontBelongs = pokemon.getMove().stream()
                    .filter(item -> !pokemonNationalDex.getMove().stream()
                            .map(i -> i.getMove().getDescription())
                            .collect(Collectors.toList()).contains(item.getMove().getDescription()))
                    .collect(Collectors.toList());

            if (!dontBelongs.isEmpty()) {
                throw new PokemonMoveIncorrectException(POKEMON_INCORRECT_MOVE + " - (" +
                        dontBelongs.stream().map(item -> item.getMove().getDescription() + " ").toString() + ")");
            }
        }
    }
}
package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonIncorretTypeException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonNameIncorrectException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.*;
import br.com.tiagocalixto.pokedex.ports.external_api.FindOneByIdExternalApiPort;
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
public class PokemonUseCaseImpl implements SaveUseCase<Pokemon>, UpdateUseCase<Pokemon>, DeleteUseCase<Long>,
         IsExistsUseCase, FindAllPageableUseCase<Pokemon>, FindAlByNameUseCase<Pokemon>, FindOneByIdUseCase<Pokemon> {

    //<editor-fold: properties>
    private FindOneByIdRepositoryPort<Pokemon> findByIdRepository;
    private FindAllByNameRepositoryPort<Pokemon> findByNameRepository;
    private FindAllPageableRepositoryPort<Pokemon> findPageableRepository;
    private ExistsByIdRepositoryPort isExistsRepository;
    private InsertRepositoryPort<Pokemon> insertRepository;
    private UpdateRepositoryPort<Pokemon> updateRepository;
    private DeleteRepositoryPort<Pokemon> deleteRepository;
    private EvolutionUseCase evolutionUseCase;
    private FindOneByIdExternalApiPort<Pokemon> nationalDex;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonUseCaseImpl (@Qualifier("PokemonRepositorySql") FindOneByIdRepositoryPort<Pokemon> findByIdRepository,
                               @Qualifier("PokemonRepositorySql") FindAllByNameRepositoryPort<Pokemon> findByNameRepository,
                               @Qualifier("PokemonRepositorySql") FindAllPageableRepositoryPort<Pokemon> findPageableRepository,
                               @Qualifier("PokemonRepositorySql") ExistsByIdRepositoryPort isExistsRepository,
                               @Qualifier("PokemonRepositorySql") InsertRepositoryPort<Pokemon> insertRepository,
                               @Qualifier("PokemonRepositorySql") UpdateRepositoryPort<Pokemon> updateRepository,
                               @Qualifier("PokemonRepositorySql") DeleteRepositoryPort<Pokemon> deleteRepository,
                               @Qualifier("EvolutionUseCaseImpl") EvolutionUseCase evolutionUseCase,
                               @Qualifier("NationalDex") FindOneByIdExternalApiPort<Pokemon> nationalDex){

        this.findByIdRepository = findByIdRepository;
        this.findByNameRepository = findByNameRepository;
        this.findPageableRepository = findPageableRepository;
        this.isExistsRepository = isExistsRepository;
        this.insertRepository = insertRepository;
        this.updateRepository = updateRepository;
        this.deleteRepository = deleteRepository;
        this.evolutionUseCase = evolutionUseCase;
        this.nationalDex = nationalDex;
    }
    //</editor-fold>


    @Override
    public Pokemon findById(Long number) {

        return findByIdRepository.findById(number)
                .orElseThrow(() -> new EntityNotFoundException(POKEMON_NOT_FOUND_BY_NUMBER + number));
    }

    @Override
    public List<Pokemon> findByName(String name) {

        List<Pokemon> pokemon = findByNameRepository.findAllByName(name);

        if(pokemon.isEmpty())
            throw new EntityNotFoundException(POKEMON_NOT_FOUND_BY_NAME + name);

        return pokemon;
    }

    @Override
    public List<Pokemon> findAllPageable(int pageNumber) {

        return findPageableRepository.findAllPageable(pageNumber, 10, NAME);
    }

    @Override
    public boolean isExistsById(Long number) {

        return isExistsRepository.isExistsById(number);
    }

    @Transactional
    @Override
    public Pokemon save(Pokemon pokemon) {


        Pokemon pokemonSaved = null;
        return null;
    }

    @Transactional
    @Override
    public Pokemon update(Pokemon pokemon) {

        Pokemon pokemonUpdated = null;
        return null;
    }

    @Transactional
    @Override
    public void delete(Long number) {

        Pokemon pokemon = this.findById(number);
        deleteRepository.delete(pokemon);
    }

    private void verifyPokemonInfo(Pokemon pokemon){

        Pokemon pokemonNationalDex = nationalDex.findById(pokemon.getNumber())
                .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

        verifyName(pokemon, pokemonNationalDex);
        verifyType(pokemon, pokemonNationalDex);

    }

    private void verifyName(Pokemon pokemon, Pokemon pokemonNationalDex){

        if(!Util.phoneticStringsMatches(pokemon.getName(), pokemonNationalDex.getName())){
            throw new PokemonNameIncorrectException(POKEMON_INCORRECT_NAME);
        }
    }

    private void verifyType(Pokemon pokemon, Pokemon pokemonNationalDex){

        List<Type> dontBelongs = pokemon.getType().stream()
                .filter(item -> !pokemonNationalDex.getType().stream()
                .map(Type::getDescription)
                .collect(Collectors.toList()).contains(item.getDescription()))
                .collect(Collectors.toList());

        if(!dontBelongs.isEmpty()){
            throw new PokemonIncorretTypeException(POKEMON_INCORRECT_TYPE + " - (" +
                    dontBelongs.stream().map(item -> item.getDescription() + ", ").toString() + ")");
        }
    }

    private void verifyMoves(Pokemon pokemon, Pokemon pokemonNationalDex){


    }
}

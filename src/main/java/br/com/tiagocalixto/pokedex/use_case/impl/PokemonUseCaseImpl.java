package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.domain.Historic;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_base_ports.*;
import br.com.tiagocalixto.pokedex.use_case.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Service("PokemonUseCaseImpl")
public class PokemonUseCaseImpl implements SaveUseCase<Pokemon>, UpdateUseCase<Pokemon>, DeleteUseCase<Long>,
         IsExistsUseCase, FindAllPageableUseCase<Pokemon>, PokemonUseCase, FindOneByIdUseCase<Pokemon> {

    //<editor-fold: properties>
    private FindOneByIdRepositoryPort<Pokemon> findByIdRepository;
    private FindAllByTextRepositoryPort<Pokemon> findByNameRepository;
    private FindAllPageableRepositoryPort<Pokemon> findPageableRepository;
    private ExistsByIdRepositoryPort isExistsRepository;
    private InsertRepositoryPort<Pokemon> insertRepository;
    private UpdateRepositoryPort<Pokemon> updateRepository;
    private DeleteRepositoryPort<Pokemon> deleteRepository;
    private EvolutionUseCase evolutionUseCase;
    private SaveUseCase<Historic> historicSaveUseCase;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonUseCaseImpl (@Qualifier("PokemonRepositorySql") FindOneByIdRepositoryPort<Pokemon> findByIdRepository,
                               @Qualifier("PokemonRepositorySql") FindAllByTextRepositoryPort<Pokemon> findByNameRepository,
                               @Qualifier("PokemonRepositorySql") FindAllPageableRepositoryPort<Pokemon> findPageableRepository,
                               @Qualifier("PokemonRepositorySql") ExistsByIdRepositoryPort isExistsRepository,
                               @Qualifier("PokemonRepositorySql") InsertRepositoryPort<Pokemon> insertRepository,
                               @Qualifier("PokemonRepositorySql") UpdateRepositoryPort<Pokemon> updateRepository,
                               @Qualifier("PokemonRepositorySql") DeleteRepositoryPort<Pokemon> deleteRepository,
                               @Qualifier("EvolutionUseCaseImpl") EvolutionUseCase evolutionUseCase,
                               @Qualifier("HistoricUseCaseImpl") SaveUseCase<Historic> historicSaveUseCase){

        this.findByIdRepository = findByIdRepository;
        this.findByNameRepository = findByNameRepository;
        this.findPageableRepository = findPageableRepository;
        this.isExistsRepository = isExistsRepository;
        this.insertRepository = insertRepository;
        this.updateRepository = updateRepository;
        this.deleteRepository = deleteRepository;
        this.evolutionUseCase = evolutionUseCase;
        this.historicSaveUseCase = historicSaveUseCase;
    }
    //</editor-fold>


    @Override
    public Pokemon findById(Long number) {

        return findByIdRepository.findById(number)
                .orElseThrow(() -> new EntityNotFoundException(POKEMON_NOT_FOUND_BY_NUMBER + number));
    }

    @Override
    public List<Pokemon> findByName(String name) {

        List<Pokemon> pokemon = findByNameRepository.findAllByText(name);

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

    @Override
    public Pokemon save(Pokemon domain) {
        return null;
    }

    @Override
    public Pokemon update(Pokemon domain) {
        return null;
    }

    @Override
    public void delete(Long number) {

        deleteRepository.delete(this.findById(number));
    }
}

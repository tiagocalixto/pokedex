package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("PokemonRepositorySql")
public class PokemonIdRepositoryAdapterSql implements InsertRepositoryPort<Pokemon>, UpdateRepositoryPort<Pokemon>,
        DeleteRepositoryPort<Pokemon>, FindAllPageableRepositoryPort<Pokemon>, FindOneByIdRepositoryPort<Pokemon>,
        FindAllByNameRepositoryPort<Pokemon>, ExistsByIdRepositoryPort {

    //<editor-fold: properties>
    private PokemonRepository repository;
    private ConverterEntitySql<PokemonEntity, Pokemon> converter;
    private FindOneByIdRepositoryPort<Pokemon> cacheFind;
    private InsertRepositoryPort<Pokemon> saveCache;
    private InsertRepositoryPort<Pokemon> mongoHistoricRepository;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonIdRepositoryAdapterSql(PokemonRepository repository,
                                         ConverterEntitySql<PokemonEntity, Pokemon> converter,
                                         @Qualifier("HistoricRepositoryMongo")
                                                 InsertRepositoryPort<Pokemon> mongoHistoricRepository,
                                         @Qualifier("PokemonCache") FindOneByIdRepositoryPort<Pokemon> cacheFind,
                                         @Qualifier("PokemonCache") InsertRepositoryPort<Pokemon> saveCache) {
        this.repository = repository;
        this.converter = converter;
        this.cacheFind = cacheFind;
        this.saveCache = saveCache;
        this.mongoHistoricRepository = mongoHistoricRepository;
    }
    //</editor-fold>


    @Override
    public Pokemon insert(Pokemon pokemon) {

        Pokemon saved = converter.convertToDomainNotOptional(
                repository.save(converter.convertToEntityNotOptional(pokemon)));

        saveCache.insertVoid(saved);
        return saved;
    }

    @Override
    public Pokemon update(Pokemon pokemon) {

        Pokemon updated = converter.convertToDomainNotOptional(
                repository.save(converter.convertToEntityNotOptional(pokemon)));

        saveCache.insertVoid(updated);
        return updated;
    }

    @Override
    public void delete(Pokemon pokemon) {

        repository.delete(converter.convertToEntityNotOptional(pokemon));
    }

    @Override
    public List<Pokemon> findAllByName(String name) {

        Set<PokemonEntity> pokemon = repository.findAllByPhoneticName(name);

        return pokemon.stream()
                .map(item -> converter.convertToDomain(repository.findById(item.getId())).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pokemon> findById(Long number) {

        Optional<Pokemon> founded = cacheFind.findById(number);

        if(founded.isEmpty()){
            founded = converter.convertToDomain(repository.findFirstByNumber(number));
            founded.ifPresent(saveCache::insertVoid);
        }

        return founded;
    }

    @Override
    public List<Pokemon> findAllPageable(int pageNumber, int size, String orderBy) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.Direction.ASC, orderBy);
        return converter.convertToDomainList(repository.findAll(pageRequest).getContent());
    }

    @Override
    public boolean isExistsById(Long id) {

        return repository.existsById(id);
    }
}

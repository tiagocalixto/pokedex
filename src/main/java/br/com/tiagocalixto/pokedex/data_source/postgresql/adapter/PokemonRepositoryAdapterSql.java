package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("PokemonRepositorySql")
public class PokemonRepositoryAdapterSql implements InsertRepositoryPort<Pokemon>, UpdateRepositoryPort<Pokemon>,
        DeleteRepositoryPort<Pokemon>, FindAllPageableRepositoryPort<Pokemon>, FindOneByIdRepositoryPort<Pokemon>,
        FindAllByNameRepositoryPort<Pokemon>, ExistsByIdRepositoryPort {

    //<editor-fold: properties>
    private PokemonRepository repository;
    private ConverterEntitySql<PokemonEntity, Pokemon> converter;
    private InsertRepositoryPort<Object> saveHistoric;
    private PreparePokemonToPersistSql prepareToPersist;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public PokemonRepositoryAdapterSql(PokemonRepository repository,
                                       PreparePokemonToPersistSql prepareToPersist,
                                       ConverterEntitySql<PokemonEntity, Pokemon> converter,
                                       @Qualifier("MongoHistoricRepository")
                                               InsertRepositoryPort<Object> saveHistoric) {

        this.repository = repository;
        this.prepareToPersist = prepareToPersist;
        this.converter = converter;
        this.saveHistoric = saveHistoric;
    }
    //</editor-fold>


    @Override
    @CachePut(value = "PokemonRepositorySql", key = "{#pokemon.id}")
    public Pokemon insert(Pokemon pokemon) {

        PokemonEntity entity = prepareToPersist
                .prepareToInsert(converter.convertToEntityNotOptional(pokemon));

        Pokemon saved = converter.convertToDomainNotOptional(
                repository.save(entity));

        saveHistoric.insertAsync(saved);

        return saved;
    }

    @Override
    @CachePut(value = "PokemonRepositorySql", key = "{#pokemon.id}")
    public Pokemon update(Pokemon pokemon) {

        PokemonEntity entity = prepareToPersist
                .prepareToUpdate(converter.convertToEntityNotOptional(pokemon));

        Pokemon updated = converter.convertToDomainNotOptional(
                repository.save(entity));

        saveHistoric.insertAsync(updated);

        return updated;
    }

    @Override
    @CacheEvict(value = "PokemonRepositorySql", key = "{#pokemon.id}")
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
    @Cacheable(value = "PokemonRepositorySql", key = "{#id}")
    public Optional<Pokemon> findById(Long id) {

        return converter.convertToDomain(repository.findFirstByNumber(id));
    }

    @Override
    public List<Pokemon> findAllPageable(int pageNumber, int size, String orderBy) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.Direction.ASC, orderBy);
        return converter.convertToDomainList(repository.findAll(pageRequest).getContent());
    }

    @Override
    public boolean isExistsById(Long id) {

        return repository.existsByNumber(id);
    }
}

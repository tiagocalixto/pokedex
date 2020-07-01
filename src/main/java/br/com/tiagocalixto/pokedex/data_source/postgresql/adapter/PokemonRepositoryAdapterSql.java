package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source.delete.DeleteRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source.find.*;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.UpdateRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("PokemonRepositorySql")
public class PokemonRepositoryAdapterSql implements DeleteRepositoryPort<Pokemon>, ExistsByNumberRepositoryPort,
        FindAllByNameRepositoryPort<Pokemon>, FindPageableRepositoryPort<Pokemon>, FindOneByIdRepositoryPort<Pokemon>,
        FindOneByNumberRepositoryPort<Pokemon>, InsertRepositoryPort<Pokemon>, UpdateRepositoryPort<Pokemon> {

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
    @Caching(put = {
            @CachePut(value = "PokemonSqlId", key = "#result.id"),
            @CachePut(value = "PokemonSqlNumber", key = "#result.number")
    })
    public Pokemon insert(Pokemon pokemon) {

        PokemonEntity entity = prepareToPersist
                .prepareToInsert(converter.convertToEntityNotOptional(pokemon));

        Pokemon saved = converter.convertToDomainNotOptional(
                repository.save(entity));

        saveHistoric.insertAsync(saved);

        return saved;
    }

    @Override
    @Caching(put = {
            @CachePut(value = "PokemonSqlId", key = "#result.id"),
            @CachePut(value = "PokemonSqlNumber", key = "#result.number")
    })
    public Pokemon update(Pokemon pokemon) {

        PokemonEntity entity = prepareToPersist
                .prepareToUpdate(converter.convertToEntityNotOptional(pokemon));

        Pokemon updated = converter.convertToDomainNotOptional(
                repository.save(entity));

        saveHistoric.insertAsync(updated);

        return updated;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "PokemonSqlId", key = "#pokemon.id"),
            @CacheEvict(value = "PokemonSqlNumber", key = "#pokemon.number")
    })
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
    @Cacheable(value = "PokemonSqlNumber", key = "#number")
    public Optional<Pokemon> findByNumber(Long number) {

        return converter.convertToDomain(repository.findFirstByNumber(number));
    }

    @Override
    public List<Pokemon> findPageable(int pageNumber, int size, String orderBy) {

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, orderBy);
        return converter.convertToDomainList(repository.findAll(pageRequest).getContent());
    }

    @Override
    public boolean isExistsByNumber(Long number) {

        return repository.existsByNumber(number);
    }

    @Override
    @Cacheable(value = "PokemonSqlId", key = "#id")
    public Optional<Pokemon> findById(Long id) {

        return converter.convertToDomain(repository.findById(id));
    }
}

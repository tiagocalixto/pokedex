package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source.delete.DeleteRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source.find.*;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.UpdateRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_AUDIT;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Component(POKEMON_REPOSITORY_SQL)
public class PokemonRepositoryAdapterSql implements DeleteRepositoryPort<Pokemon>, ExistsByNumberRepositoryPort,
        FindAllByNameRepositoryPort<Pokemon>, FindPageableRepositoryPort<Pokemon>, FindOneByIdRepositoryPort<Pokemon>,
        FindOneByNumberRepositoryPort<Pokemon>, InsertRepositoryPort<Pokemon>, UpdateRepositoryPort<Pokemon> {

    //<editor-fold: properties>
    private PokemonRepository repository;
    private ConverterEntitySql<PokemonEntity, Pokemon> converter;
    private InsertRepositoryPort<Object> audit;
    private PreparePokemonToPersistSql prepareToPersist;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonRepositoryAdapterSql(PokemonRepository repository,
                                       PreparePokemonToPersistSql prepareToPersist,
                                       ConverterEntitySql<PokemonEntity, Pokemon> converter,
                                       @Qualifier(POKEMON_AUDIT)
                                               InsertRepositoryPort<Object> audit) {

        this.repository = repository;
        this.prepareToPersist = prepareToPersist;
        this.converter = converter;
        this.audit = audit;
    }
    //</editor-fold>


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Pokemon insert(Pokemon pokemon) {

        log.info("Inserting pokemon on postgres - {}", pokemon);

        PokemonEntity entity = prepareToPersist
                .prepareToInsert(converter.convertToEntityNotOptional(pokemon));

        Pokemon saved = converter.convertToDomainNotOptional(
                repository.save(entity));

        audit.insertAsync(saved);

        log.info("Pokemon successfully inserted on postgres with id {}", saved.getId());
        return saved;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Pokemon update(Pokemon pokemon) {

        log.info("Updating pokemon on postgres - {}", pokemon);

        PokemonEntity entity = prepareToPersist
                .prepareToUpdate(converter.convertToEntityNotOptional(pokemon));

        Pokemon updated = converter.convertToDomainNotOptional(
                repository.save(entity));

        audit.insertAsync(updated);

        log.info("Pokemon successfully updated on postgres");
        return updated;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(Pokemon pokemon) {

        log.info("Logical deleting pokemon from postgres, pokemon {}", pokemon);

        repository.deleteById(pokemon.getId());

        log.info("Pokemon successfully deleted from postgres");
    }

    @Override
    public List<Pokemon> findAllByName(String name) {

        Set<Long> pokemon = repository.findAllByPhoneticName(name);

        return pokemon.stream()
                .map(item -> converter.convertToDomainNotOptional(repository.findById(item).orElse(null)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pokemon> findByNumber(Long number) {

        log.info("select optional pokemon for number in postgres, number = {}", number);

        return converter.convertToDomain(repository.findFirstByNumber(number));
    }

    @Override
    public List<Pokemon> findPageable(int pageNumber, int size, String orderBy) {

        log.info("select pageable in postgres, Page number = {}, order by = {}", pageNumber, orderBy);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, orderBy);
        return converter.convertToDomainList(repository.findAll(pageRequest).getContent());
    }

    @Override
    public Optional<Pokemon> findById(Long id) {

        log.info("select optional pokemon for id in postgres, id = {}", id);
        return converter.convertToDomain(repository.findById(id));
    }

    @Override
    public boolean isExistsByNumber(Long number) {

        log.info("verify if pokemon exists  in postgres by number {}", number);
        return repository.existsByNumber(number);
    }
}

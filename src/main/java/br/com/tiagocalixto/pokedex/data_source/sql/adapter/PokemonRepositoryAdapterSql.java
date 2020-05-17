package br.com.tiagocalixto.pokedex.data_source.sql.adapter;

import br.com.tiagocalixto.pokedex.data_source.sql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.HistoricEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.HistoricRepository;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.data_source_ports.PokemonFindRepositoryPort;
import br.com.tiagocalixto.pokedex.data_source_ports.PokemonMaintenanceRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.INSERT;

@Component("PokemonRepositorySql")
public class PokemonRepositoryAdapterSql extends GenericAdapterSql implements PokemonFindRepositoryPort,
                                                                              PokemonMaintenanceRepositoryPort {

    private PokemonRepository repository;
    private ConverterEntitySql<PokemonEntity, Pokemon> converter;

    @Autowired
    public PokemonRepositoryAdapterSql(HistoricRepository historicRepository, PokemonRepository repository,
                                       ConverterEntitySql<PokemonEntity, Pokemon> converter){
        super(historicRepository);
        this.repository = repository;
        this.converter = converter;
    }


    @Override
    public Pokemon insert(Pokemon pokemon) {

        return converter.convertToDomainNotOptional(
                repository.save(converter.convertToEntityNotOptional(pokemon)));
    }

    @Override
    public Pokemon update(Pokemon pokemon) {

        return converter.convertToDomainNotOptional(
                repository.save(converter.convertToEntityNotOptional(pokemon)));
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Optional<Pokemon> findByName(String name) {

        return converter.convertToDomain(repository.findFirstByNameIgnoreCase(name));
    }

    @Override
    public Optional<Pokemon> findByNumber(Long number) {

        return converter.convertToDomain(repository.findFirstByNumber(number));
    }

    @Override
    public List<Pokemon> findAllPageable(int pageNumber, int size, String orderBy) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.Direction.ASC, orderBy);
        return converter.convertToDomainList(repository.findAll(pageRequest).getContent());
    }

    @Override
    public Optional<Pokemon> findById(Long id) {

        return converter.convertToDomain(repository.findById(id));
    }

    @Override
    public boolean isExistsById(Long id) {

        return repository.existsById(id);
    }

    @Override
    public void saveHistoric(Pokemon pokemon, String action) {

        long version = 1L;

        if (!action.equalsIgnoreCase(INSERT)) {

            List<HistoricEntity> historicList = findHistoricByIdEntity(pokemon.getId());
            HistoricEntity lastHistoric = historicList.stream().max(Comparator.comparing(HistoricEntity::getVersion))
                    .orElse(HistoricEntity.builder().version(0L).build());
            version = lastHistoric.getVersion() + 1;
        }

        HistoricEntity historic = HistoricEntity.builder()
                .id(0L)
                .action(action)
                .idEntity(pokemon.getId())
                .entity(Util.convertObjectToJson(pokemon))
                .version(version)
                .build();

        saveHistoric(historic);
    }
}

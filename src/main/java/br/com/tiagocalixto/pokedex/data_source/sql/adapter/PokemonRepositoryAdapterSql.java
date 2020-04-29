package br.com.tiagocalixto.pokedex.data_source.sql.adapter;

import br.com.tiagocalixto.pokedex.data_source.sql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.PokemonFindRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.PokemonMaintenanceRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("PokemonRepositorySql")
public class PokemonRepositoryAdapterSql implements PokemonFindRepositoryPort, PokemonMaintenanceRepositoryPort {

    @Autowired
    PokemonRepository repository;

    @Autowired
    ConverterEntitySql<PokemonEntity, Pokemon> converter;


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
    public void saveHistoric(Pokemon pokemon) {

    }
}

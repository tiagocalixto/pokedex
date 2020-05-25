package br.com.tiagocalixto.pokedex.data_base.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_base.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_base.postgresql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.ports.data_base_port.*;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("PokemonRepositorySql")
public class PokemonRepositoryAdapterSql implements InsertRepositoryPort<Pokemon>, UpdateRepositoryPort<Pokemon>, DeleteRepositoryPort<Long>,
        FindAllPageableRepositoryPort<Pokemon>, FindEntityByRepositoryPort<Pokemon, Long>, FindListByRepositoryPort<Pokemon, String>, ExistsRepositoryPort<Long> {

    private PokemonRepository repository;
    private ConverterEntitySql<PokemonEntity, Pokemon> converter;

    @Autowired
    public PokemonRepositoryAdapterSql(PokemonRepository repository,
                                       ConverterEntitySql<PokemonEntity, Pokemon> converter) {
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
    public List<Pokemon> findListBy(String name) {

        Set<PokemonEntity> pokemon = repository.findAllByPhoneticName(name);

        return pokemon.stream()
                .map(item -> converter.convertToDomain(repository.findById(item.getId())).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pokemon> findBy(Long number) {

        return converter.convertToDomain(repository.findFirstByNumber(number));
    }

    @Override
    public List<Pokemon> findAllPageable(int pageNumber, int size, String orderBy) {

        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.Direction.ASC, orderBy);
        return converter.convertToDomainList(repository.findAll(pageRequest).getContent());
    }

    @Override
    public boolean isExists(Long id) {

        return repository.existsById(id);
    }
}

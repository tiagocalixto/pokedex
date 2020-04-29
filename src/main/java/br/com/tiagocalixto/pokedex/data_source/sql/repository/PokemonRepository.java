package br.com.tiagocalixto.pokedex.data_source.sql.repository;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends CrudRepository<PokemonEntity, Long> {

    Optional<PokemonEntity> findFirstByNameIgnoreCase(String name);
    Optional<PokemonEntity> findFirstByNumber(Long number);
    Page<PokemonEntity> findAll(Pageable pageable);
}

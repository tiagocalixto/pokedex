package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PokemonRepository extends CrudRepository<PokemonEntity, Long> {

    Optional<PokemonEntity> findFirstByNumber(Long number);
    Page<PokemonEntity> findAll(Pageable pageable);

    //todo  - CREATE EXTENSION fuzzystrmatch; on postgres in deploy
    @Query(value = "select distinct * from pokemon p where soundex(p.name) = soundex(:name)", nativeQuery = true)
    Set<PokemonEntity> findAllByPhoneticName(@Param("name") String name);
}

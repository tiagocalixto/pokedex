package br.com.tiagocalixto.pokedex.data_source.sql.repository;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<TypeEntity, Long> {

    @Query(value = "SELECT * FROM types WHERE unaccent(lower(description)) = " +
            "unaccent(lower(:description)) AND deleted != true LIMIT 1",  nativeQuery = true)
    Optional<TypeEntity> findFirstByDescriptionIgnoreCaseAndIgnoreAccents(@Param("description") String description);
}

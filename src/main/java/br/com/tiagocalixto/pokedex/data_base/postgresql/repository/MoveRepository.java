package br.com.tiagocalixto.pokedex.data_base.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.MoveEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends CrudRepository<MoveEntity, Long> {

    @Query(value = "SELECT * FROM moves WHERE unaccent(lower(description)) = " +
            "unaccent(lower(:description)) AND deleted != true LIMIT 1", nativeQuery = true)
    Optional<MoveEntity> findFirstByDescriptionIgnoreCaseAndIgnoreAccents(@Param("description") String description);
}

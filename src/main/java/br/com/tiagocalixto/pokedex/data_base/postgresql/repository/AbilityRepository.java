package br.com.tiagocalixto.pokedex.data_base.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.AbilityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbilityRepository extends CrudRepository<AbilityEntity, Long> {

    @Query(value = "SELECT * FROM abilities WHERE unaccent(lower(description)) = " +
            "unaccent(lower(:description)) AND deleted != true LIMIT 1", nativeQuery = true)
    Optional<AbilityEntity> findFirstByDescriptionIgnoreCaseAndIgnoreAccents(@Param("description") String description);
}

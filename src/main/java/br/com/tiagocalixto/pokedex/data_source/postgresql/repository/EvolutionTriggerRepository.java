package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionTriggerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvolutionTriggerRepository extends CrudRepository<EvolutionTriggerEntity, Long> {

    @Query(value = "SELECT * FROM evolution_trigger WHERE unaccent(lower(description)) = " +
            "unaccent(lower(:description)) AND deleted != true LIMIT 1", nativeQuery = true)
    Optional<EvolutionTriggerEntity> findFirstByDescriptionIgnoreCaseAndIgnoreAccents(@Param("description")
                                                                                              String description);
}

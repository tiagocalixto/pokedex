package br.com.tiagocalixto.pokedex.data_base.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.EvolutionStoneEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvolutionStoneRepository extends CrudRepository<EvolutionStoneEntity, Long> {

    @Query(value = "SELECT * FROM evolution_stones WHERE unaccent(lower(description)) = " +
            "unaccent(lower(:description)) AND deleted != true LIMIT 1", nativeQuery = true)
    Optional<EvolutionStoneEntity> findFirstByDescriptionIgnoreCaseAndIgnoreAccents(@Param("description")
                                                                                              String description);
}

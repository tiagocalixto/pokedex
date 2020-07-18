package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.AbilityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbilityRepository extends CrudRepository<AbilityEntity, Long> {

    Optional<AbilityEntity> findFirstByDescriptionIgnoringCase(String description);
}

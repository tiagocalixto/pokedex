package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<TypeEntity, Long> {

    Optional<TypeEntity> findFirstByDescriptionIgnoringCase(String description);
}

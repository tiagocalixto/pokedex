package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.MoveEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends CrudRepository<MoveEntity, Long> {

    Optional<MoveEntity> findFirstByDescriptionIgnoringCase(String description);
}

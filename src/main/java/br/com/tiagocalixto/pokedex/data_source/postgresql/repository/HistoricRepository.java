package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.HistoricEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricRepository extends CrudRepository<HistoricEntity, Long> {


    @Query(value = "select * from historic where id_entity = :idEntity " +
            "order by version desc limit 1", nativeQuery = true)
    Optional<HistoricEntity> findHistoricLastVersionByIdEntity(@Param("idEntity") Long idEntity);
}

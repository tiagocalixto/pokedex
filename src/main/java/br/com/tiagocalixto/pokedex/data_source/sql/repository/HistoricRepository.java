package br.com.tiagocalixto.pokedex.data_source.sql.repository;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.HistoricEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricRepository extends CrudRepository<HistoricEntity, Long> {

    List<HistoricEntity> findByIdEntityOrderByVersion(Long idEntity);
}

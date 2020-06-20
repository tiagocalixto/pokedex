package br.com.tiagocalixto.pokedex.data_source.mongodb.repository;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.HistoricCollectionMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricRepositoryMongo extends MongoRepository<HistoricCollectionMongo, String> {

    Optional<HistoricCollectionMongo> findFirstByEntityNameAndIdEntityOrderByVersionAsc(String entityName,
                                                                                        String idEntity);
}

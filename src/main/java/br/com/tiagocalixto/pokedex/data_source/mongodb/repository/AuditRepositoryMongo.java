package br.com.tiagocalixto.pokedex.data_source.mongodb.repository;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.AuditCollectionMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepositoryMongo extends MongoRepository<AuditCollectionMongo, String> {

    Optional<AuditCollectionMongo> findFirstByEntityNameAndIdEntityOrderByVersionAsc(String entityName,
                                                                                     String idEntity);
}

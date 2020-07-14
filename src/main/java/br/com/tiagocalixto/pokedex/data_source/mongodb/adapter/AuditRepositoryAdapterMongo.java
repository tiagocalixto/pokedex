package br.com.tiagocalixto.pokedex.data_source.mongodb.adapter;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.AuditCollectionMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.repository.AuditRepositoryMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.repository.MongoDbSequence;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.EMPTY;
import static br.com.tiagocalixto.pokedex.infra.util.constant.ConstantComponentName.POKEMON_AUDIT;

@Component(POKEMON_AUDIT)
public class AuditRepositoryAdapterMongo implements InsertRepositoryPort<Object> {

    //<editor-fold: properties>
    private MongoDbSequence sequenceGenerator;
    private AuditRepositoryMongo repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public AuditRepositoryAdapterMongo(MongoDbSequence sequenceGenerator,
                                       AuditRepositoryMongo repository) {
        this.sequenceGenerator = sequenceGenerator;
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public Object insert(Object entity) {

        createAudit(entity).ifPresent(repository::save);
        return entity;
    }

    private Optional<AuditCollectionMongo> createAudit(Object entity) {

        String idEntity = getEntityId(entity);

        if (idEntity.equalsIgnoreCase(EMPTY)) {
            return Optional.empty();
        }

        AuditCollectionMongo historic = AuditCollectionMongo.builder()
                .id(sequenceGenerator.nextId(AuditCollectionMongo.SEQUENCE_NAME))
                .date(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .entityName(entity.getClass().getSimpleName())
                .idEntity(idEntity)
                .version(getNextVersion(idEntity, entity.getClass().getSimpleName()))
                .entity(entity)
                .build();

        return Optional.of(historic);
    }

    private String getEntityId(Object entity) {

        try {
            Class<?> clazz = entity.getClass();
            Field field = org.springframework.util.ReflectionUtils.findField(clazz, "id");
            org.springframework.util.ReflectionUtils.makeAccessible(field);
            var id = field.get(entity);
            return id.toString();

        } catch (Exception ex) {
            return EMPTY;
        }
    }

    private String getNextVersion(String idEntity, String entityName) {

        Long version = Long.valueOf(repository.findFirstByEntityNameAndIdEntityOrderByVersionAsc(entityName, idEntity)
                .map(AuditCollectionMongo::getVersion).orElse("0")) + 1L;

        return version.toString();
    }
}

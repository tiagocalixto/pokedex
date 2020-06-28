package br.com.tiagocalixto.pokedex.data_source.mongodb.adapter;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.HistoricCollectionMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.repository.HistoricRepositoryMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.repository.MongoDbSequence;
import br.com.tiagocalixto.pokedex.ports.data_source.persist.InsertRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.EMPTY;

@Component("MongoHistoricRepository")
public class HistoricRepositoryAdapterMongo implements InsertRepositoryPort<Object> {

    //<editor-fold: properties>
    private MongoDbSequence sequenceGenerator;
    private HistoricRepositoryMongo repository;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public HistoricRepositoryAdapterMongo(MongoDbSequence sequenceGenerator,
                                          HistoricRepositoryMongo repository) {
        this.sequenceGenerator = sequenceGenerator;
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public Object insert(Object entity) {

        createHistoric(entity).ifPresent(repository::save);
        return entity;
    }

    private Optional<HistoricCollectionMongo> createHistoric(Object entity) {

        String idEntity = getEntityId(entity);

        if (idEntity.equalsIgnoreCase(EMPTY)) {
            return Optional.empty();
        }

        HistoricCollectionMongo historic = HistoricCollectionMongo.builder()
                .id(sequenceGenerator.nextId(HistoricCollectionMongo.SEQUENCE_NAME))
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
                .map(HistoricCollectionMongo::getVersion).orElse("0")) + 1L;

        return version.toString();
    }
}

package br.com.tiagocalixto.pokedex.data_source.mongodb.repository;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.SequenceCollectionMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.SEQUENCE_GENERATOR_MONGO;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
@Component(SEQUENCE_GENERATOR_MONGO)
public class SequenceGeneratorMongo {

    private MongoOperations mongoOperations;

    public SequenceGeneratorMongo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    public String nextId(String seqName) {

        log.info("Generating next mongo id for {}", seqName);
        SequenceCollectionMongo counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                SequenceCollectionMongo.class);

        Long id = !Objects.isNull(counter) ? counter.getSeq() : 1;

        log.info("Mongo id successfully generated {}", id);
        return id.toString();
    }
}

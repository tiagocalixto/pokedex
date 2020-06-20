package br.com.tiagocalixto.pokedex.data_source.mongodb.repository;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.MongoDbSequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component("MongoDbSequence")
public class MongoDbSequence {

    private MongoOperations mongoOperations;

    @Autowired
    public MongoDbSequence(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String nextId(String seqName) {

        MongoDbSequences counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                MongoDbSequences.class);

        Long id = !Objects.isNull(counter) ? counter.getSeq() : 1;

        return id.toString();
    }
}
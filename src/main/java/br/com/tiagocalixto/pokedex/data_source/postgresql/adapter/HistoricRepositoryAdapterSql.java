package br.com.tiagocalixto.pokedex.data_source.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.HistoricEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.repository.HistoricRepository;
import br.com.tiagocalixto.pokedex.domain.Historic;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.INSERT;

@Component("historicRepositorySql")
public class HistoricRepositoryAdapterSql implements InsertRepositoryPort<Historic> {

    private HistoricRepository repository;
    private ConverterEntitySql<HistoricEntity, Historic> converter;

    @Autowired
    public HistoricRepositoryAdapterSql(HistoricRepository historicRepository,
                                        ConverterEntitySql<HistoricEntity, Historic> converter) {

        this.repository = historicRepository;
        this.converter = converter;
    }


    @Override
    public Historic insert(Historic historic) {

        HistoricEntity entity = converter.convertToEntityNotOptional(historic);
        entity.setVersion(getHistoricVersion(entity));

        return converter.convertToDomainNotOptional(repository.save(entity));
    }

    private Long getHistoricVersion(HistoricEntity historic){

        Long version = 1L;

        if (!historic.getAction().equalsIgnoreCase(INSERT)) {

            version = repository.findHistoricLastVersionByIdEntity(historic.getIdEntity())
                    .map(HistoricEntity::getVersion)
                    .orElse(1L);
        }

        return version;
    }
}

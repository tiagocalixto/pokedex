package br.com.tiagocalixto.pokedex.data_base.postgresql.adapter;

import br.com.tiagocalixto.pokedex.data_base.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.HistoricEntity;
import br.com.tiagocalixto.pokedex.data_base.postgresql.repository.HistoricRepository;
import br.com.tiagocalixto.pokedex.ports.data_base_port.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.domain.Historic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.INSERT;

@Component("historicRepositorySql")
public class HistoricRepositoryAdapterSql implements InsertRepositoryPort<Historic> {

    private HistoricRepository historicRepository;
    private ConverterEntitySql<HistoricEntity, Historic> converter;

    @Autowired
    public HistoricRepositoryAdapterSql(HistoricRepository historicRepository,
                                        ConverterEntitySql<HistoricEntity, Historic> converter) {

        this.historicRepository = historicRepository;
        this.converter = converter;
    }


    @Override
    public Historic insert(Historic historic) {

        HistoricEntity entity = converter.convertToEntityNotOptional(historic);

        long version = 1L;

        if (!entity.getAction().equalsIgnoreCase(INSERT)) {

            List<HistoricEntity> historicList = historicRepository.findByIdEntityOrderByVersion(entity.getId());
            HistoricEntity lastHistoric = historicList.stream().max(Comparator.comparing(HistoricEntity::getVersion))
                    .orElse(HistoricEntity.builder().version(0L).build());
            version = lastHistoric.getVersion() + 1;
        }

        entity.setVersion(version);

        return converter.convertToDomainNotOptional(historicRepository.save(entity));
    }
}

package br.com.tiagocalixto.pokedex.data_base.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_base.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_base.postgresql.entity.HistoricEntity;
import br.com.tiagocalixto.pokedex.domain.Historic;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("historicConverterEntity")
public class ConverterHistoricEntitySql implements ConverterEntitySql<HistoricEntity, Historic> {


    @Override
    public Optional<HistoricEntity> convertToEntity(Optional<Historic> domain) {

        if(domain.isEmpty())
            return Optional.empty();

        HistoricEntity historicEntity = HistoricEntity.builder().build();

        domain.ifPresent(item -> {
            historicEntity.setId(0L);
            historicEntity.setAction(item.getAction());
            historicEntity.setIdEntity(item.getIdEntity());
            historicEntity.setEntity(item.getEntity());
        });

        return Optional.of(historicEntity);
    }

    @Override
    public Optional<Historic> convertToDomain(Optional<HistoricEntity> entity) {

        if(entity.isEmpty())
            return Optional.empty();

        Historic historic = Historic.builder().build();

        entity.ifPresent(item -> {
            historic.setId(item.getId());
            historic.setAction(item.getAction());
            historic.setIdEntity(item.getIdEntity());
            historic.setEntity(item.getEntity());
        });

        return Optional.of(historic);
    }
}

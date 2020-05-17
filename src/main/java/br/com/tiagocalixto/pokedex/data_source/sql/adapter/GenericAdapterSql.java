package br.com.tiagocalixto.pokedex.data_source.sql.adapter;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.HistoricEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.HistoricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenericAdapterSql {

    private HistoricRepository historicRepository;

    @Autowired
    public GenericAdapterSql(HistoricRepository historicRepository){
        this.historicRepository = historicRepository;
    }


    protected void saveHistoric(HistoricEntity historic){

        historicRepository.save(historic);
    }

    protected List<HistoricEntity> findHistoricByIdEntity(Long idEntity){

        return historicRepository.findByIdEntityOrderByVersion(idEntity);
    }

}

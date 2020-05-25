package br.com.tiagocalixto.pokedex.data_source.sql.adapter;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.HistoricEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.HistoricRepository;
import br.com.tiagocalixto.pokedex.data_source_ports.InsertPort;
import br.com.tiagocalixto.pokedex.data_source_ports.SaveHistoricPort;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.INSERT;

@Component
public class PokemonHistoricRepositoryAdapterSql implements SaveHistoricPort<Pokemon> {

    private HistoricRepository historicRepository;

    @Autowired
    public PokemonHistoricRepositoryAdapterSql(HistoricRepository historicRepository){
        this.historicRepository = historicRepository;
    }


    @Override
    public void saveHistoric(Pokemon pokemon, String action) {

        long version = 1L;

        if (!action.equalsIgnoreCase(INSERT)) {

            List<HistoricEntity> historicList = historicRepository.findByIdEntityOrderByVersion(pokemon.getId());
            HistoricEntity lastHistoric = historicList.stream().max(Comparator.comparing(HistoricEntity::getVersion))
                    .orElse(HistoricEntity.builder().version(0L).build());
            version = lastHistoric.getVersion() + 1;
        }

        HistoricEntity historic = HistoricEntity.builder()
                .id(0L)
                .action(action)
                .idEntity(pokemon.getId())
                .entity(Util.convertObjectToJson(pokemon))
                .version(version)
                .build();

        historicRepository.save(historic);
    }
}

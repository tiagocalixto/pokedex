package br.com.tiagocalixto.pokedex.data_source.mongodb.adapter;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import org.springframework.stereotype.Component;

@Component("HistoricRepositoryMongo")
public class HistoricRepositoryAdapterMongo implements InsertRepositoryPort<Pokemon> {


    @Override
    public Pokemon insert(Pokemon domain) {
        return null;
    }
}

package br.com.tiagocalixto.pokedex.data_source_ports;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;

public interface PokemonMaintenanceRepositoryPort extends MaintenanceGenericRepositoryPort<Pokemon> {

    void saveHistoric(Pokemon pokemon, String action);
}

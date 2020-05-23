package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.national_db_api;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.evolution_chain.EvolutionChainNationalDb;

import java.util.Optional;

public interface EvolutionChainApiRepository {

    void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain);
    Optional<EvolutionChainNationalDb> getEvolutions(Long number);
}

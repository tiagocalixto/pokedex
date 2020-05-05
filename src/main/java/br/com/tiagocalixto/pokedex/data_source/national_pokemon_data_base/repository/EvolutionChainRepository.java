package br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.repository;

import br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.entity.evolution_chain.EvolutionChainEvolveToApi;

import java.util.List;

public interface EvolutionChainRepository {

    void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain);
    List<EvolutionChainEvolveToApi> getEvolutions(Long number);
}

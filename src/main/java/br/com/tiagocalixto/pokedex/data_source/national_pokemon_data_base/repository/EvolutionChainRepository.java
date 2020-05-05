package br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.repository;

import br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.entity.evolution_chain.EvolutionChainPokemonApi;

public interface EvolutionChainRepository {

    void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain);
    EvolutionChainPokemonApi getEvolutions(Long number);
}

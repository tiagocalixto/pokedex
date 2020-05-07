package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.evolution_chain.EvolutionChainPokemonApi;

public interface EvolutionChainApiRepository {

    void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain);
    EvolutionChainPokemonApi getEvolutions(Long number);
}

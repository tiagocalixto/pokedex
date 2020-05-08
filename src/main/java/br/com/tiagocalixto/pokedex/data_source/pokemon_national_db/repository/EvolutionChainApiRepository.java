package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.evolution_chain.EvolutionChainPokemonApi;

import java.util.Optional;

public interface EvolutionChainApiRepository {

    void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain);
    Optional<EvolutionChainPokemonApi> getEvolutions(Long number);
}

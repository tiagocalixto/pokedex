package br.com.tiagocalixto.pokedex.integration.national_dex.client;

import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolutionChainNationalDexDto;

import java.util.Optional;

public interface EvolutionChainClient {

    void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain);
    Optional<EvolutionChainNationalDexDto> getEvolutions(Long number);
}

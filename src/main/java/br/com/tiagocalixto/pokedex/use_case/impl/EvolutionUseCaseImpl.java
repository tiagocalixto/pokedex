package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.ports.external_api.FindOneByIdExternalApiPort;
import br.com.tiagocalixto.pokedex.use_case.EvolutionUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("EvolutionUseCaseImpl")
public class EvolutionUseCaseImpl implements EvolutionUseCase {

    private FindOneByIdExternalApiPort<Pokemon> nationalDex;

    public EvolutionUseCaseImpl( @Qualifier("NationalDex") FindOneByIdExternalApiPort<Pokemon> nationalDex){
        this.nationalDex = nationalDex;
    }


    @Override
    public PokemonEvolution associateOrInsert(PokemonEvolution evolution) {

        return null;

    }
}

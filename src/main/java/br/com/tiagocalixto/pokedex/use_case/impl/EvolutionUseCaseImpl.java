package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.use_case.EvolutionUseCase;
import org.springframework.stereotype.Service;

@Service("EvolutionUseCaseImpl")
public class EvolutionUseCaseImpl implements EvolutionUseCase {


    @Override
    public PokemonEvolution associateOrInsert(PokemonEvolution evolution) {
        return null;
    }
}

package br.com.tiagocalixto.pokedex.use_case.pokemon.find;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;

public interface FindOneByNumberUseCase {

    Pokemon execute(Long number);
}

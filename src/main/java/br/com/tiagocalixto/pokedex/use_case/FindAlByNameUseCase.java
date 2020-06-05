package br.com.tiagocalixto.pokedex.use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;

import java.util.List;

public interface FindAlByNameUseCase<T> {

    List<T> findByName(String name);
}

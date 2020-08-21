package br.com.tiagocalixto.pokedex.use_case.business.pokemon.find;

import java.util.List;

public interface FindAllByNameUseCase<T> {

    List<T> execute(String name);
}

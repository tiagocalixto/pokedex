package br.com.tiagocalixto.pokedex.use_case.pokemon.find;

import java.util.List;

public interface FindAllByNameUseCase<T> {

    List<T> execute(String name);
}

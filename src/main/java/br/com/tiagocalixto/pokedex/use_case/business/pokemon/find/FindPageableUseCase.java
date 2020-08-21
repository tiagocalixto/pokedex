package br.com.tiagocalixto.pokedex.use_case.business.pokemon.find;

import java.util.List;

public interface FindPageableUseCase<T> {

    List<T> execute(int pageNumber);
}

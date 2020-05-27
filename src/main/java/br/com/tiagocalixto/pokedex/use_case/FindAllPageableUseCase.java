package br.com.tiagocalixto.pokedex.use_case;

import java.util.List;

public interface FindAllPageableUseCase<T> {

    List<T> findAllPageable(int pageNumber);
}

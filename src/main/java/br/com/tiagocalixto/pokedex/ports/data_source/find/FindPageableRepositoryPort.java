package br.com.tiagocalixto.pokedex.ports.data_source.find;

import java.util.List;

public interface FindPageableRepositoryPort<T> {

    List<T> findPageable(int pageNumber, int size, String orderBy);
}

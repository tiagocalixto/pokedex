package br.com.tiagocalixto.pokedex.ports.data_base_port;

import java.util.List;

public interface FindAllPageableRepositoryPort<T> {

    List<T> findAllPageable(int pageNumber, int size, String orderBy);
}

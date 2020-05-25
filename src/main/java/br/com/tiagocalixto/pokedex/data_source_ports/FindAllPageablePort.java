package br.com.tiagocalixto.pokedex.data_source_ports;

import java.util.List;

public interface FindAllPageablePort<T> {

    List<T> findAllPageable(int pageNumber, int size, String orderBy);
}

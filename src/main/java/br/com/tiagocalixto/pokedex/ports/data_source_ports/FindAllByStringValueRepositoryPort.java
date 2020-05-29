package br.com.tiagocalixto.pokedex.ports.data_source_ports;

import java.util.List;

public interface FindAllByStringValueRepositoryPort<T> {

    List<T> findAllByText(String value);
}

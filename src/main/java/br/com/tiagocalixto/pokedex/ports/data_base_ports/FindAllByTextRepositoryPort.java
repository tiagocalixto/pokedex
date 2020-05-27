package br.com.tiagocalixto.pokedex.ports.data_base_ports;

import java.util.List;

public interface FindAllByTextRepositoryPort<T> {

    List<T> findAllByText(String text);
}

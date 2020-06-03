package br.com.tiagocalixto.pokedex.ports.data_source_ports;

import java.util.List;

public interface FindAllByNameRepositoryPort<T> {

    List<T> findAllByName(String name);
}

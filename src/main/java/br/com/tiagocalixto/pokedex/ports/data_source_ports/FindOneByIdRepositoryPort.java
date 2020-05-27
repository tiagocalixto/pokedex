package br.com.tiagocalixto.pokedex.ports.data_source_ports;

import java.util.Optional;

public interface FindOneByIdRepositoryPort<T> {

    Optional<T> findById(Long value);
}

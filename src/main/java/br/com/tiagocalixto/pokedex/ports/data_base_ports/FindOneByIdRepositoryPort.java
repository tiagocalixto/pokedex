package br.com.tiagocalixto.pokedex.ports.data_base_ports;

import java.util.Optional;

public interface FindOneByIdRepositoryPort<T> {

    Optional<T> findById(Long value);
}

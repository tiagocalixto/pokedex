package br.com.tiagocalixto.pokedex.data_source_ports;

import java.util.Optional;

public interface FindGenericRepositoryPort<T> {

    Optional<T> findById(Long id);
    boolean isExistsById(Long id);
}

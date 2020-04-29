package br.com.tiagocalixto.pokedex.ports;

import java.util.Optional;

public interface FindGenericRepositoryPort<T> {

    Optional<T> findById(Long id);
    boolean isExistsById(Long id);
}

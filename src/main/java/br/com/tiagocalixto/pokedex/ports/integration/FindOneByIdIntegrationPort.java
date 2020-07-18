package br.com.tiagocalixto.pokedex.ports.integration;

import java.util.Optional;

public interface FindOneByIdIntegrationPort<T> {

    Optional<T> findById(Long id);
}

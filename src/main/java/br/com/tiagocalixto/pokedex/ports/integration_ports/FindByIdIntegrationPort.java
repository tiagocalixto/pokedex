package br.com.tiagocalixto.pokedex.ports.integration_ports;

import java.util.Optional;

public interface FindByIdIntegrationPort<T> {

    Optional<T> findById(Long id);
}

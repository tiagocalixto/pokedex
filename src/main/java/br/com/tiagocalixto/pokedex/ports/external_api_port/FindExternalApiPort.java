package br.com.tiagocalixto.pokedex.ports.external_api_port;

import java.util.Optional;

public interface FindExternalApiPort<T> {

    Optional<T> findById(Long id);
}

package br.com.tiagocalixto.pokedex.ports.external_api;

import java.util.Optional;

public interface FindOneByIdExternalApiPort<T> {

    Optional<T> findById(Long id);
}

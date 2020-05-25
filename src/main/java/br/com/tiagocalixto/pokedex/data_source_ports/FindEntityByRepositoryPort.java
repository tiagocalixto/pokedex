package br.com.tiagocalixto.pokedex.data_source_ports;

import java.util.Optional;

public interface FindEntityByRepositoryPort<T, B> {

    Optional<T> findBy(B value);
}

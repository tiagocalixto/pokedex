package br.com.tiagocalixto.pokedex.ports.data_base_port;

import java.util.Optional;

public interface FindEntityByRepositoryPort<T, B> {

    Optional<T> findBy(B value);
}

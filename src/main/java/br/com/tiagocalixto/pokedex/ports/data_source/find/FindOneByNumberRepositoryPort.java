package br.com.tiagocalixto.pokedex.ports.data_source.find;

import java.util.Optional;

public interface FindOneByNumberRepositoryPort<T> {

    Optional<T> findByNumber(Long number);
}

package br.com.tiagocalixto.pokedex.ports.data_source.find;

import java.util.Optional;

public interface FindOneByIdRepositoryPort<T> {

    Optional<T> findById(Long id);
}

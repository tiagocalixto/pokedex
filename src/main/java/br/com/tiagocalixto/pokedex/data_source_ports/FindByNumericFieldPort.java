package br.com.tiagocalixto.pokedex.data_source_ports;

import java.util.Optional;

public interface FindByNumericFieldPort<T> {

    Optional<T> findBy(Long value);
}

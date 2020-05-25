package br.com.tiagocalixto.pokedex.data_source_ports;

import java.util.Optional;

public interface FindByStringFieldPort<T> {

    Optional<T> findBy(String value);
}

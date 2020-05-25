package br.com.tiagocalixto.pokedex.data_source_ports;

import java.util.List;

public interface FindListByRepositoryPort<T, B> {

    List<T> findListBy(B value);
}

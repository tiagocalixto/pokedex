package br.com.tiagocalixto.pokedex.ports.data_source_ports;

public interface InsertRepositoryPort<T> {

    T insert(T domain);
}

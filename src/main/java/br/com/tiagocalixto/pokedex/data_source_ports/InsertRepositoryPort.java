package br.com.tiagocalixto.pokedex.data_source_ports;

public interface InsertRepositoryPort<T> {

    T insert(T domain);
}

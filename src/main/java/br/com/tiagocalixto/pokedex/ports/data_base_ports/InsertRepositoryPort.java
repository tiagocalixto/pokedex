package br.com.tiagocalixto.pokedex.ports.data_base_ports;

public interface InsertRepositoryPort<T> {

    T insert(T domain);
}

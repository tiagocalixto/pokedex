package br.com.tiagocalixto.pokedex.ports.data_source_ports;

public interface DeleteRepositoryPort<T> {

    void delete(T domain);
}

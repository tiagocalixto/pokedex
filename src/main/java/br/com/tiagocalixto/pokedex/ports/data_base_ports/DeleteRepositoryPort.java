package br.com.tiagocalixto.pokedex.ports.data_base_ports;

public interface DeleteRepositoryPort<T> {

    void delete(T domain);
}

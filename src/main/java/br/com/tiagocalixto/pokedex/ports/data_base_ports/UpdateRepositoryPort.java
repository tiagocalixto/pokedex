package br.com.tiagocalixto.pokedex.ports.data_base_ports;

public interface UpdateRepositoryPort<T> {

    T update(T domain);
}

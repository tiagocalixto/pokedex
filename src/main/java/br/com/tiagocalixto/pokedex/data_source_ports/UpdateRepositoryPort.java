package br.com.tiagocalixto.pokedex.data_source_ports;

public interface UpdateRepositoryPort<T> {

    T update(T domain);
}

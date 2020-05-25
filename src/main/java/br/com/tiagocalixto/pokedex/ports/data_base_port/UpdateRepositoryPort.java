package br.com.tiagocalixto.pokedex.ports.data_base_port;

public interface UpdateRepositoryPort<T> {

    T update(T domain);
}

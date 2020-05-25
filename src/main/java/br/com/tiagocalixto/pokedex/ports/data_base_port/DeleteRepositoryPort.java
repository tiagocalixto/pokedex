package br.com.tiagocalixto.pokedex.ports.data_base_port;

public interface DeleteRepositoryPort<T> {

    void delete(T domain);
}

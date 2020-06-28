package br.com.tiagocalixto.pokedex.ports.data_source.delete;

public interface DeleteRepositoryPort<T> {

    void delete(T domain);
}

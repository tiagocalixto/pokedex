package br.com.tiagocalixto.pokedex.ports.data_source.persist;

public interface UpdateRepositoryPort<T> {

    T update(T domain);
}

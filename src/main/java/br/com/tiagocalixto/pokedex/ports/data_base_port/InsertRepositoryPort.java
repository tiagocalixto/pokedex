package br.com.tiagocalixto.pokedex.ports.data_base_port;

public interface InsertRepositoryPort<T> {

    T insert(T domain);
}

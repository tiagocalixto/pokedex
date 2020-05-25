package br.com.tiagocalixto.pokedex.ports.data_base_port;

public interface ExistsRepositoryPort<T> {

    boolean isExists(T value);
}

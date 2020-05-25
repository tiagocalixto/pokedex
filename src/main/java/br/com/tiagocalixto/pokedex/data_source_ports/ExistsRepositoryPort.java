package br.com.tiagocalixto.pokedex.data_source_ports;

public interface ExistsRepositoryPort<T> {

    boolean isExists(T value);
}

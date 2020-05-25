package br.com.tiagocalixto.pokedex.data_source_ports;

public interface DeletePort<T> {

    void delete(T domain);
}

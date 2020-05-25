package br.com.tiagocalixto.pokedex.data_source_ports;

public interface UpdatePort<T> {

    T update(T domain);
}

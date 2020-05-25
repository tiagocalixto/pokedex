package br.com.tiagocalixto.pokedex.data_source_ports;

public interface InsertPort<T> {

    T insert(T domain);
}

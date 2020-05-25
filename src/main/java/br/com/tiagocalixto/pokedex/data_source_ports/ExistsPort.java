package br.com.tiagocalixto.pokedex.data_source_ports;

public interface ExistsPort<T> {

    boolean isExists(T value);
}

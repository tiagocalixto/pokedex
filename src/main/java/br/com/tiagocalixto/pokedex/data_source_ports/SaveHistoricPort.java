package br.com.tiagocalixto.pokedex.data_source_ports;

public interface SaveHistoricPort<T> {

    void saveHistoric(T domain, String action);
}

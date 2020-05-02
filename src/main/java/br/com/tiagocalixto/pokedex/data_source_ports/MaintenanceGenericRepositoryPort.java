package br.com.tiagocalixto.pokedex.data_source_ports;

public interface MaintenanceGenericRepositoryPort<T> {

    T insert(T domain);
    T update(T domain);
    void delete(Long id);
}

package br.com.tiagocalixto.pokedex.ports.data_source_ports;

import org.springframework.scheduling.annotation.Async;

public interface InsertRepositoryPort<T> {

    T insert(T domain);

    @Async
    default void insertVoid(T domain){
        insert(domain);
    }
}

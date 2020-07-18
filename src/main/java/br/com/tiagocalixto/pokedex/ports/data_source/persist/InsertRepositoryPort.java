package br.com.tiagocalixto.pokedex.ports.data_source.persist;

import org.springframework.scheduling.annotation.Async;

public interface InsertRepositoryPort<T> {

    T insert(T domain);

    @Async
    default void insertAsync(T domain){
        insert(domain);
    }
}

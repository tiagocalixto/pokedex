package br.com.tiagocalixto.pokedex.ports.entry_point.put;

public interface PutEntryPointPort<T> {

    T update(T domain);
}

package br.com.tiagocalixto.pokedex.ports.entry_point.post;

public interface PostEntryPointPort<T> {

    T save(T domain);
}

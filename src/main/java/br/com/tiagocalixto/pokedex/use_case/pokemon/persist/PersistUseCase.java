package br.com.tiagocalixto.pokedex.use_case.pokemon.persist;

public interface PersistUseCase<T> {

    T execute(T domain);
}

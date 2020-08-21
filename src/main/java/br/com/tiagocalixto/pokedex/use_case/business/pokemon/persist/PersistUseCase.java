package br.com.tiagocalixto.pokedex.use_case.business.pokemon.persist;

public interface PersistUseCase<T> {

    T execute(T domain);
}

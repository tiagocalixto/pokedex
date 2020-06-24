package br.com.tiagocalixto.pokedex.use_case;

public interface PersistUseCase<T> {

    T execute(T domain);
}

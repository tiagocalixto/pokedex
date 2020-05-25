package br.com.tiagocalixto.pokedex.use_case;

public interface SaveUseCase<T> {

    T save(T domain);
}

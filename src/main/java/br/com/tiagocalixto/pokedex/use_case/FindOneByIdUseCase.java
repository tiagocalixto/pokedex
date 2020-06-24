package br.com.tiagocalixto.pokedex.use_case;

public interface FindOneByIdUseCase<T> {

    T execute(Long id);
}

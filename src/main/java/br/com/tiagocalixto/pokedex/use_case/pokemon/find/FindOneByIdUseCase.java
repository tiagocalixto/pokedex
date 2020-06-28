package br.com.tiagocalixto.pokedex.use_case.pokemon.find;

public interface FindOneByIdUseCase<T> {

    T execute(Long id);
}

package br.com.tiagocalixto.pokedex.use_case.business.pokemon.find;

public interface FindOneByIdUseCase<T> {

    T execute(Long id);
}

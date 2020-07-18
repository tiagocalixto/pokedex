package br.com.tiagocalixto.pokedex.ports.entry_point.get;

public interface GetOneByIdEntryPointPort<T> {

    T getOneById(Long id);
}

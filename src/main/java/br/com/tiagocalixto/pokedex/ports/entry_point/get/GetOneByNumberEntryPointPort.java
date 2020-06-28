package br.com.tiagocalixto.pokedex.ports.entry_point.get;

public interface GetOneByNumberEntryPointPort<T> {

    T getOneByNumber(Long number);
}

package br.com.tiagocalixto.pokedex.ports.entry_point.get;

import java.util.List;

public interface GetAllByNameEntryPointPort<T> {

    List<T> getAllByName(String name);
}

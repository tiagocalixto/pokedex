package br.com.tiagocalixto.pokedex.ports.entry_point.get;

import java.util.List;

public interface GetPageableEntryPointPort<T> {

    List<T> getPage(int pageNumber);
}

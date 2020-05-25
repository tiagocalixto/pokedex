package br.com.tiagocalixto.pokedex.ports.data_base_port;

import java.util.List;

public interface FindListByRepositoryPort<T, B> {

    List<T> findListBy(B value);
}

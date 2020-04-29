package br.com.tiagocalixto.pokedex.ports;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;

import java.util.List;
import java.util.Optional;

public interface PokemonFindRepositoryPort extends FindGenericRepositoryPort<Pokemon> {

    Optional<Pokemon> findByName(String name);
    Optional<Pokemon> findByNumber(Long number);
    List<Pokemon> findAllPageable(int pageNumber, int size, String orderBy);
}

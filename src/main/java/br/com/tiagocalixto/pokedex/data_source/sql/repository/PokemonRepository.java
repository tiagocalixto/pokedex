package br.com.tiagocalixto.pokedex.data_source.sql.repository;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends CrudRepository<PokemonEntity, Long> {
}

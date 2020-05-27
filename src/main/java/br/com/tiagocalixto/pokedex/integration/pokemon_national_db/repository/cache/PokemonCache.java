package br.com.tiagocalixto.pokedex.integration.pokemon_national_db.repository.cache;

import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.pokemon.PokemonNationalDb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonCache extends CrudRepository<PokemonNationalDb, Long> {
}

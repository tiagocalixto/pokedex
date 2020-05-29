package br.com.tiagocalixto.pokedex.external_api.through_cache.repository;

import br.com.tiagocalixto.pokedex.external_api.through_cache.entity.PokemonCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepositoryCache extends CrudRepository<PokemonCache, Long> {
}

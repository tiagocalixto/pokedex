package br.com.tiagocalixto.pokedex.data_source.cache.repository;

import br.com.tiagocalixto.pokedex.data_source.cache.entity.PokemonNationalDexCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonNationalDexRepositoryCache extends CrudRepository<PokemonNationalDexCache, Long> {
}

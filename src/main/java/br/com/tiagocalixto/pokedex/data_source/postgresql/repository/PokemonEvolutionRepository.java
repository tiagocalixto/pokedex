package br.com.tiagocalixto.pokedex.data_source.postgresql.repository;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEvolutionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonEvolutionRepository extends CrudRepository<PokemonEvolutionEntity, Long> {

    @Modifying
    @Query(value = "UPDATE pokemon_evolution SET deleted = true, " +
            "        audit_logical_delete_date = current_timestamp " +
            "        WHERE  id_pokemon_fk = :idPokemon or " +
            "        id_evolution_fk = :idPokemon", nativeQuery = true)
    void deleteAllByIdPokemonFk(@Param("idPokemon") Long idPokemon);
}

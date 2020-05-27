package br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.pokemon;

import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.AbilityNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.MoveNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.TypeNationalDb;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RedisHash(value = "pokemon", timeToLive = 2592000) //ttl = 30 days
public class PokemonNationalDb implements Serializable {

    @Id
    private Long id;
    private Long number;
    private String name;
    private Long weight;
    private BigDecimal height;
    private String urlPicture;
    private PokemonStatsNationalDb stats;
    private List<TypeNationalDb> type;
    private PokemonSpecieNationalDb specie;
    private List<MoveNationalDb> move;
    private List<AbilityNationalDb> ability;
}

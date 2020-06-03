package br.com.tiagocalixto.pokedex.data_source.cache.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@RedisHash(value = "pokemonNationalDex", timeToLive = 604800) //ttl = 7 days
public class PokemonNationalDexCache implements Serializable {

    @Id
    Long id;
    String body;
}

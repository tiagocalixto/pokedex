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
@RedisHash(value = "pokemonNationalDex", timeToLive = 86400) //ttl = 24 hours
public class PokemonNationalDexCache implements Serializable {

    @Id
    Long id;
    String body;
}

package br.com.tiagocalixto.pokedex.domain.pokemon;

import br.com.tiagocalixto.pokedex.domain.Move;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PokemonMove implements Serializable {

    private Move move;
    private Long levelLearn;
}

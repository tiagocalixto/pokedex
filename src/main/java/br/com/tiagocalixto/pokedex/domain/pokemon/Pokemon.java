package br.com.tiagocalixto.pokedex.domain.pokemon;

import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Pokemon implements Serializable {

    private Long number;
    private String name;
    private Long weight;
    private BigDecimal height;
    private String about;
    private String urlPicture;
    private PokemonStats stats;
    private List<Type> type;
    private PokemonEvolution evolveTo;
    private PokemonEvolution evolvedFrom;
    private List<PokemonMove> moves;
    private List<Ability> abilities;
    private List<Type> weaknesses;
}

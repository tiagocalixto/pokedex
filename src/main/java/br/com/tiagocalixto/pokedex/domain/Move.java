package br.com.tiagocalixto.pokedex.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Move implements Serializable {

    private String description;
    private Long pp;
    private Long power;
    private BigDecimal accuracy;
    private Type type;
    private String about;
}

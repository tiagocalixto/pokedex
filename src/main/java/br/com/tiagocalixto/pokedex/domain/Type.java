package br.com.tiagocalixto.pokedex.domain;

import br.com.tiagocalixto.pokedex.domain.enums.TypeEnum;
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
public class Type implements Serializable {

    private TypeEnum description;
}

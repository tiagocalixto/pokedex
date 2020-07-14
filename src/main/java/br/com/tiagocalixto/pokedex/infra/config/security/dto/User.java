package br.com.tiagocalixto.pokedex.infra.config.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    private String userName;
    private String password;
    private String[] roles;
}

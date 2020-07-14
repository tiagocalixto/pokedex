package br.com.tiagocalixto.pokedex.infra.config.security.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "myauth")
public class UserConfiguration {

    @Setter(AccessLevel.NONE)
    private final List<User> users = new ArrayList<>();
}

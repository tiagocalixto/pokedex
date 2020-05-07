package br.com.tiagocalixto.pokedex.infra.config.spring_bean;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("br.com.tiagocalixto.pokedex")
public class SpringBeanConfig {

    @Bean
    public PokeApi pokeApi(){
        return new PokeApiClient();
    }
}

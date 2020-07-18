package br.com.tiagocalixto.pokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PokedexApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokedexApplication.class, args);
    }

}

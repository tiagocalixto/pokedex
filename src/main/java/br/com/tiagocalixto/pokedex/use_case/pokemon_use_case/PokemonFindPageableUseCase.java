package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.NoContentException;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.FindAllPageableRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.FindPageableUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.NAME;
import static br.com.tiagocalixto.pokedex.infra.util.Constant.PAGE_HAS_NO_CONTENT;

@Slf4j
@Component("PokemonFindAllPageableUseCase")
public class PokemonFindPageableUseCase implements FindPageableUseCase<Pokemon> {

    @Value("${pokemon.max.register.pageable}")
    private int maxPokemonPage;
    private FindAllPageableRepositoryPort<Pokemon> repository;

    public PokemonFindPageableUseCase(@Qualifier("PokemonRepositorySql")
                                                 FindAllPageableRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }


    @Override
    public List<Pokemon> execute(int pageNumber) {

        log.info("Find pokemon pageable, page number {}", pageNumber);
        List<Pokemon> pokemon = repository.findAllPageable(pageNumber, maxPokemonPage, NAME);

        if(pokemon.isEmpty()){
            log.info(PAGE_HAS_NO_CONTENT);
            throw new NoContentException(PAGE_HAS_NO_CONTENT);
        }

        log.info("{} pokemon(s) founded in page {}", pokemon.size(), pageNumber);
        return pokemon;
    }
}

package br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.impl;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.infra.exception.NoContentException;
import br.com.tiagocalixto.pokedex.ports.data_source.find.FindPageableRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.FindPageableUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.NUMBER;
import static br.com.tiagocalixto.pokedex.infra.constant.Constant.PAGE_HAS_NO_CONTENT;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_FIND_PAGEABLE_USE_CASE;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Service(POKEMON_FIND_PAGEABLE_USE_CASE)
public class PokemonFindPageableUseCase implements FindPageableUseCase<Pokemon> {

    //<editor-fold: properties>
    @Value("${pokemon.max.register.pageable}")
    private int maxPokemonPage;
    private FindPageableRepositoryPort<Pokemon> repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonFindPageableUseCase(@Qualifier(POKEMON_REPOSITORY_SQL)
                                              FindPageableRepositoryPort<Pokemon> repository) {
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public List<Pokemon> execute(int pageNumber) {

        log.info("Find pokemon pageable, page number {}", pageNumber);
        List<Pokemon> pokemon = repository.findPageable(pageNumber, maxPokemonPage, NUMBER);

        if (pokemon.isEmpty()) {
            log.info(PAGE_HAS_NO_CONTENT);
            throw new NoContentException(PAGE_HAS_NO_CONTENT);
        }

        log.info("{} pokemon(s) founded in page {}", pokemon.size(), pageNumber);
        return pokemon;
    }
}

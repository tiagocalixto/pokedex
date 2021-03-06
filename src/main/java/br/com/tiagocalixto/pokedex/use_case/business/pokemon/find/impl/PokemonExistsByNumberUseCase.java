package br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.impl;

import br.com.tiagocalixto.pokedex.ports.data_source.find.ExistsByNumberRepositoryPort;
import br.com.tiagocalixto.pokedex.use_case.business.pokemon.find.ExistsByNumberUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_EXISTS_BY_NUMBER_USE_CASE;
import static br.com.tiagocalixto.pokedex.infra.constant.ConstantComponentName.POKEMON_REPOSITORY_SQL;

@Slf4j
@Service(POKEMON_EXISTS_BY_NUMBER_USE_CASE)
public class PokemonExistsByNumberUseCase implements ExistsByNumberUseCase {

    //<editor-fold: properties>
    private ExistsByNumberRepositoryPort repository;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonExistsByNumberUseCase(@Qualifier(POKEMON_REPOSITORY_SQL)
                                                ExistsByNumberRepositoryPort repository) {
        this.repository = repository;
    }
    //</editor-fold>


    @Override
    public boolean execute(Long number) {

        log.info("Verify if pokemon exists by number {}", number);
        boolean pokemon = repository.isExistsByNumber(number);

        if (!pokemon) {
            log.info("Pokemon with number {} not exists", number);
            return false;
        }

        log.info("Pokemon with number {} founded", number);
        return true;
    }
}

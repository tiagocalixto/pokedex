package br.com.tiagocalixto.pokedex.use_case.pokemon_use_case;

import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.infra.exception.NationalDexOutOfServiceException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonIncorrectTypeException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonMoveIncorrectException;
import br.com.tiagocalixto.pokedex.infra.exception.PokemonNameIncorrectException;
import br.com.tiagocalixto.pokedex.infra.util.Util;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import br.com.tiagocalixto.pokedex.use_case.PersistUseCase;
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediator;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Getter
@SuppressWarnings("Duplicates") //todo remove this
@Component("PokemonPersistAbstract")
public abstract class PokemonPersistAbstract implements PersistUseCase<Pokemon> {

    private PokemonMediator mediator;
    private FindOneByIdIntegrationPort<Pokemon> nationalDex;

    public PokemonPersistAbstract(PokemonMediator mediator,
                                  @Qualifier("NationalDex")
                                          FindOneByIdIntegrationPort<Pokemon> nationalDex) {

        this.mediator = mediator;
        this.nationalDex = nationalDex;
    }


    protected Pokemon verifyPokemonInfo(Pokemon pokemon) {

        Pokemon pokemonNationalDex = nationalDex.findById(pokemon.getNumber())
                .orElseThrow(() -> new NationalDexOutOfServiceException(NATIONAL_DEX_UNAVAILABLE));

        verifyName(pokemon, pokemonNationalDex);
        verifyType(pokemon, pokemonNationalDex);
        verifyMove(pokemon, pokemonNationalDex);
        pokemon.setEvolvedFrom(mediator.prepareEvolvedFrom(pokemon.getEvolvedFrom()));
        pokemon.setEvolveTo(mediator.prepareEvolveTo(pokemon.getEvolveTo()));

        return pokemon;
    }

    private void verifyName(Pokemon pokemon, Pokemon pokemonNationalDex) {

        if (!Util.phoneticStringsMatches(pokemon.getName(), pokemonNationalDex.getName())) {
            throw new PokemonNameIncorrectException(POKEMON_INCORRECT_NAME);
        }
    }

    private void verifyType(Pokemon pokemon, Pokemon pokemonNationalDex) {

        List<Type> dontBelongs = pokemon.getType().stream()
                .filter(item -> !pokemonNationalDex.getType().stream()
                        .map(Type::getDescription)
                        .collect(Collectors.toList()).contains(item.getDescription()))
                .collect(Collectors.toList());

        if (!dontBelongs.isEmpty()) {
            throw new PokemonIncorrectTypeException(POKEMON_INCORRECT_TYPE + " - (" +
                    dontBelongs.stream().map(item -> item.getDescription() + " ").toString() + ")");
        }
    }

    private void verifyMove(Pokemon pokemon, Pokemon pokemonNationalDex) {

        if (!pokemon.getMove().isEmpty()) {

            List<PokemonMove> dontBelongs = pokemon.getMove().stream()
                    .filter(item -> !pokemonNationalDex.getMove().stream()
                            .map(i -> i.getMove().getDescription())
                            .collect(Collectors.toList()).contains(item.getMove().getDescription()))
                    .collect(Collectors.toList());

            if (!dontBelongs.isEmpty()) {
                throw new PokemonMoveIncorrectException(POKEMON_INCORRECT_MOVE + " - (" +
                        dontBelongs.stream().map(item -> item.getMove().getDescription() + " ").toString() + ")");
            }
        }
    }
}

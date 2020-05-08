package br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.impl;

import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.AbilityApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.MoveApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.TypeApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.entity.pokemon.PokemonStatsApi;
import br.com.tiagocalixto.pokedex.data_source.pokemon_national_db.repository.PokemonApiRepository;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Component
public class PokemonApiRepositoryImpl implements PokemonApiRepository {

    private Pokemon pokemon;
    private PokeApi pokeApi;

    @Autowired
    public PokemonApiRepositoryImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getPokemonFromNationalDataBase(Integer idPokemon) {

        this.pokemon = pokeApi.getPokemon(idPokemon);
    }

    @Override
    public Optional<PokemonApi> getPokemon() {

        if (this.pokemon == null)
            return Optional.empty();

        return Optional.of(PokemonApi.builder()
                .name(this.pokemon.getName())
                .number(Long.valueOf(this.pokemon.getId()))
                .height(BigDecimal.valueOf(this.pokemon.getHeight()))
                .weight(Long.valueOf(this.pokemon.getWeight()))
                .stats(this.getStats(this.pokemon.getStats()))
                .ability(this.getAbility(this.pokemon.getAbilities()))
                .move(this.getMove(this.pokemon.getMoves()))
                .type(this.getType(this.pokemon.getTypes()))
                .build());
    }

    private PokemonStatsApi getStats(List<PokemonStat> stat) {

        return PokemonStatsApi.builder()
                .attack(stat.stream()
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(ATTACK))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .defense(stat.stream()
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(DEFENSE))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .speed(stat.stream()
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(SPEED))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .hp(stat.stream()
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(HP))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .specialAttack(stat.stream()
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(SPECIAL_ATTACK))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .specialDefense(stat.stream()
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(SPECIAL_ATTACK))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .build();
    }

    private List<AbilityApi> getAbility(List<PokemonAbility> abilities) {

        return abilities.stream()
                .map(item -> AbilityApi.builder()
                        .about(EMPTY)
                        .description(item.getAbility().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<MoveApi> getMove(List<PokemonMove> moves) {

        return moves.stream()
                .map(item -> MoveApi.builder()
                        .about(EMPTY)
                        .description(item.getMove().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TypeApi> getType(List<PokemonType> types) {

        return types.stream()
                .map(item -> TypeApi.builder()
                        .description(item.getType().getName())
                        .build())
                .collect(Collectors.toList());
    }
}

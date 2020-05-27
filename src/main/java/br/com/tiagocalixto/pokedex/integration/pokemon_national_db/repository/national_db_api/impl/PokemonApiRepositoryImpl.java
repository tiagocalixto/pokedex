package br.com.tiagocalixto.pokedex.integration.pokemon_national_db.repository.national_db_api.impl;

import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.AbilityNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.MoveNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.TypeNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.pokemon.PokemonNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.entity.pokemon.PokemonStatsNationalDb;
import br.com.tiagocalixto.pokedex.integration.pokemon_national_db.repository.national_db_api.PokemonApiRepository;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

public class PokemonApiRepositoryImpl implements PokemonApiRepository {

    private Pokemon pokemon;
    private PokeApi pokeApi;

    public PokemonApiRepositoryImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getPokemonFromNationalDataBase(Integer idPokemon) {

        this.pokemon = pokeApi.getPokemon(idPokemon);
    }

    @Override
    public Optional<PokemonNationalDb> getPokemon() {

        if (this.pokemon == null)
            return Optional.empty();

        return Optional.of(PokemonNationalDb.builder()
                .id(Long.valueOf(this.pokemon.getId()))
                .name(this.pokemon.getName())
                .number(Long.valueOf(this.pokemon.getId()))
                .height(BigDecimal.valueOf(this.pokemon.getHeight()))
                .weight(Long.valueOf(this.pokemon.getWeight()))
                .urlPicture("https://img.pokemondb.net/artwork/" + this.pokemon.getName().toLowerCase() + ".jpg")
                .stats(this.getStats(this.pokemon.getStats()))
                .ability(this.getAbility(this.pokemon.getAbilities()))
                .move(this.getMove(this.pokemon.getMoves()))
                .type(this.getType(this.pokemon.getTypes()))
                .build());
    }

    private PokemonStatsNationalDb getStats(List<PokemonStat> stat) {

        return PokemonStatsNationalDb.builder()
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
                        .filter(item -> item.getStat().getName().equalsIgnoreCase(SPECIAL_DEFENSE))
                        .mapToLong(PokemonStat::getBaseStat)
                        .findFirst().orElse(0L))
                .build();
    }

    private List<AbilityNationalDb> getAbility(List<PokemonAbility> abilities) {

        return abilities.stream()
                .map(item -> AbilityNationalDb.builder()
                        .description(item.getAbility().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<MoveNationalDb> getMove(List<PokemonMove> moves) {

        return moves.stream()
                .map(item -> MoveNationalDb.builder()
                        .about(EMPTY)
                        .description(item.getMove().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TypeNationalDb> getType(List<PokemonType> types) {

        return types.stream()
                .map(item -> TypeNationalDb.builder()
                        .description(item.getType().getName())
                        .build())
                .collect(Collectors.toList());
    }
}

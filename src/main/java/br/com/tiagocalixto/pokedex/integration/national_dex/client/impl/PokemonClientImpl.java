package br.com.tiagocalixto.pokedex.integration.national_dex.client.impl;

import br.com.tiagocalixto.pokedex.integration.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonStatsNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.client.PokemonClient;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;

public class PokemonClientImpl implements PokemonClient {

    private Pokemon pokemon;
    private PokeApi pokeApi;

    public PokemonClientImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getPokemonFromNationalDataBase(Integer idPokemon) {

        this.pokemon = pokeApi.getPokemon(idPokemon);
    }

    @Override
    public Optional<PokemonNationalDexDto> getPokemon() {

        if (this.pokemon == null)
            return Optional.empty();

        return Optional.of(PokemonNationalDexDto.builder()
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

    private PokemonStatsNationalDexDto getStats(List<PokemonStat> stat) {

        return PokemonStatsNationalDexDto.builder()
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

    private List<AbilityNationalDexDto> getAbility(List<PokemonAbility> abilities) {

        return abilities.stream()
                .map(item -> AbilityNationalDexDto.builder()
                        .description(item.getAbility().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<MoveNationalDexDto> getMove(List<PokemonMove> moves) {

        return moves.stream()
                .map(item -> MoveNationalDexDto.builder()
                        .about(EMPTY)
                        .description(item.getMove().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TypeNationalDexDto> getType(List<PokemonType> types) {

        return types.stream()
                .map(item -> TypeNationalDexDto.builder()
                        .description(item.getType().getName())
                        .build())
                .collect(Collectors.toList());
    }
}

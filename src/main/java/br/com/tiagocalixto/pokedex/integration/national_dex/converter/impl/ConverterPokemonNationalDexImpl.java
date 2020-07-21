package br.com.tiagocalixto.pokedex.integration.national_dex.converter.impl;

import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.AbilityNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.MoveNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.TypeNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolveToNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.evolution_chain.EvolvedFromNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonStatsNationalDexDto;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.EMPTY;

@Component
public class ConverterPokemonNationalDexImpl implements ConverterNationalDex<PokemonNationalDexDto, Pokemon> {

    private ConverterNationalDex<AbilityNationalDexDto, Ability> abilityConverter;
    private ConverterNationalDex<MoveNationalDexDto, Move> moveConverter;
    private ConverterNationalDex<TypeNationalDexDto, Type> typeConverter;

    public ConverterPokemonNationalDexImpl(ConverterNationalDex<AbilityNationalDexDto, Ability> abilityConverter,
                                           ConverterNationalDex<MoveNationalDexDto, Move> moveConverter,
                                           ConverterNationalDex<TypeNationalDexDto, Type> typeConverter) {
        this.abilityConverter = abilityConverter;
        this.moveConverter = moveConverter;
        this.typeConverter = typeConverter;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Pokemon> convertToDomain(Optional<PokemonNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Pokemon pokemon = Pokemon.builder().build();

        dto.ifPresent(item -> {
            pokemon.setId(item.getId());
            pokemon.setNumber(item.getNumber());
            pokemon.setName(item.getName());
            pokemon.setWeight(item.getWeight());
            pokemon.setHeight(item.getHeight());
            pokemon.setAbout(EMPTY);
            pokemon.setUrlPicture(item.getUrlPicture());
            this.convertStats(Optional.ofNullable(item.getStats())).ifPresent(pokemon::setStats);
            pokemon.setType(this.typeConverter.convertToDomainList(item.getType()));
            pokemon.setMove(this.convertPokemonMove(item.getMove()));
            pokemon.setAbility(this.abilityConverter.convertToDomainList(item.getAbility()));
            pokemon.setWeakness(Collections.emptyList());
            this.convertPokemonEvolvedFrom(Optional.ofNullable(item.getSpecie().getEvolvedFrom()))
                    .ifPresent(pokemon::setEvolvedFrom);
            pokemon.setEvolveTo(this.convertPokemonEvolveToList(item.getSpecie().getEvolveTo().getEvolveTo()));

        });

        return Optional.of(pokemon);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonStats> convertStats(Optional<PokemonStatsNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        PokemonStats stats = PokemonStats.builder().build();

        dto.ifPresent(item -> {
            stats.setSpeed(item.getSpeed());
            stats.setSpecialDefense(item.getSpecialDefense());
            stats.setSpecialAttack(item.getSpecialAttack());
            stats.setHp(item.getHp());
            stats.setDefense(item.getDefense());
            stats.setAttack(item.getAttack());
        });

        return Optional.of(stats);
    }

    private Optional<PokemonMove> convertMove(Optional<MoveNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        PokemonMove pokemonMove = PokemonMove.builder().build();

        dto.ifPresent(item -> {
            pokemonMove.setLevelLearn(item.getLevelLearn());
            this.moveConverter.convertToDomain(Optional.ofNullable(item)).ifPresent(pokemonMove::setMove);
        });

        return Optional.of(pokemonMove);
    }

    private List<PokemonMove> convertPokemonMove(List<MoveNationalDexDto> moves) {

        if(moves == null)
            return Collections.emptyList();

        return moves.stream()
                .map(item -> convertMove(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<PokemonEvolution> convertPokemonEvolvedFrom(Optional<EvolvedFromNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        PokemonEvolution pokemonEvolution = PokemonEvolution.builder().build();

        dto.ifPresent(item ->
                pokemonEvolution.setPokemon(
                        Pokemon.builder()
                                .id(0L)
                                .number(item.getId())
                                .name(item.getName())
                                .build()
                )
        );

        return Optional.of(pokemonEvolution);
    }

    private Optional<PokemonEvolution> convertPokemonEvolveTo(Optional<EvolveToNationalDexDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        PokemonEvolution pokemonEvolution = PokemonEvolution.builder().build();

        dto.ifPresent(item ->
                pokemonEvolution.setPokemon(
                        Pokemon.builder()
                                .id(0L)
                                .number(item.getNumber())
                                .name(item.getName())
                                .type(this.typeConverter.convertToDomainList(item.getType()))
                                .build()
                )
        );

        return Optional.of(pokemonEvolution);
    }

    private List<PokemonEvolution> convertPokemonEvolveToList(List<EvolveToNationalDexDto> listEvolveTo) {

        if(listEvolveTo == null)
            return Collections.emptyList();

        return listEvolveTo.stream()
                .map(item -> this.convertPokemonEvolveTo(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

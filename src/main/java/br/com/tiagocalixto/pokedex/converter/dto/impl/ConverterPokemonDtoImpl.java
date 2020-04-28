package br.com.tiagocalixto.pokedex.converter.dto.impl;

import br.com.tiagocalixto.pokedex.controller.dto.AbilityDto;
import br.com.tiagocalixto.pokedex.controller.dto.MoveDto;
import br.com.tiagocalixto.pokedex.controller.dto.TypeDto;
import br.com.tiagocalixto.pokedex.controller.dto.pokemon.*;
import br.com.tiagocalixto.pokedex.converter.dto.ConverterDto;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConverterPokemonDtoImpl implements ConverterDto<PokemonDto, Pokemon> {

    @Autowired
    ConverterDto<TypeDto, Type> convertType;

    @Autowired
    ConverterDto<MoveDto, Move> convertMove;

    @Autowired
    ConverterDto<AbilityDto, Ability> convertAbility;


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<PokemonDto> convertToDto(Optional<Pokemon> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonDto pokemonDto = PokemonDto.builder().build();

        domain.ifPresent(item -> {
            pokemonDto.setName(item.getName());
            pokemonDto.setNumber(item.getNumber());
            pokemonDto.setWeight(item.getWeight());
            pokemonDto.setHeight(item.getHeight());
            pokemonDto.setAbout(item.getAbout());
            pokemonDto.setUrlPicture(item.getUrlPicture());
            this.convertStatsToDto(Optional.of(item.getStats())).ifPresent(pokemonDto::setStats);
            pokemonDto.setType(convertType.convertToDtoList(item.getType()));
            this.convertEvolutionToDto(Optional.ofNullable(item.getEvolveTo())).ifPresent(pokemonDto::setEvolveTo);
            this.convertEvolutionToDto(Optional.ofNullable(item.getEvolvedFrom())).ifPresent(pokemonDto::setEvolvedFrom);
            pokemonDto.setAbility(convertAbility.convertToDtoList(item.getAbility()));
            pokemonDto.setMove(this.convertPokemonMoveToDto(item.getMove()));
            pokemonDto.setWeakness(convertType.convertToDtoList(item.getWeakness()));
        });

        return Optional.of(pokemonDto);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Pokemon> convertToDomain(Optional<PokemonDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Pokemon pokemon = Pokemon.builder().build();

        dto.ifPresent(item -> {
            pokemon.setId(0L);
            pokemon.setName(item.getName());
            pokemon.setNumber(item.getNumber());
            pokemon.setWeight(item.getWeight());
            pokemon.setHeight(item.getHeight());
            pokemon.setAbout(item.getAbout());
            pokemon.setUrlPicture(item.getUrlPicture());
            this.convertStatsToDomain(Optional.of(item.getStats())).ifPresent(pokemon::setStats);
            pokemon.setType(convertType.convertToDomainList(item.getType()));
            this.convertEvolutionToDomain(Optional.ofNullable(item.getEvolveTo())).ifPresent(pokemon::setEvolveTo);
            this.convertEvolutionToDomain(Optional.ofNullable(item.getEvolvedFrom())).ifPresent(pokemon::setEvolvedFrom);
            pokemon.setAbility(convertAbility.convertToDomainList(item.getAbility()));
            pokemon.setMove(this.convertPokemonMoveToDomain(item.getMove()));
            pokemon.setWeakness(convertType.convertToDomainList(item.getWeakness()));
        });

        return Optional.of(pokemon);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonStatsDto> convertStatsToDto(Optional<PokemonStats> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonStatsDto pokemonStatsDto = PokemonStatsDto.builder().build();

        domain.ifPresent(item -> {
            pokemonStatsDto.setAttack(item.getAttack());
            pokemonStatsDto.setDefense(item.getDefense());
            pokemonStatsDto.setHp(item.getHp());
            pokemonStatsDto.setSpecialAttack(item.getSpecialAttack());
            pokemonStatsDto.setSpecialDefense(item.getSpecialDefense());
            pokemonStatsDto.setSpeed(item.getSpeed());
        });

        return Optional.of(pokemonStatsDto);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonStats> convertStatsToDomain(Optional<PokemonStatsDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        PokemonStats pokemonStats = PokemonStats.builder().build();

        dto.ifPresent(item -> {
            pokemonStats.setAttack(item.getAttack());
            pokemonStats.setDefense(item.getDefense());
            pokemonStats.setHp(item.getHp());
            pokemonStats.setSpecialAttack(item.getSpecialAttack());
            pokemonStats.setSpecialDefense(item.getSpecialDefense());
            pokemonStats.setSpeed(item.getSpeed());
        });

        return Optional.of(pokemonStats);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonAbbreviatedDto> convertAbbreviatedToDto(Optional<Pokemon> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonAbbreviatedDto pokemonAbbreviatedDto = PokemonAbbreviatedDto.builder().build();

        domain.ifPresent(item -> {
            pokemonAbbreviatedDto.setName(item.getName());
            pokemonAbbreviatedDto.setNumber(item.getNumber());
            pokemonAbbreviatedDto.setUrlPicture(item.getUrlPicture());
            pokemonAbbreviatedDto.setType(convertType.convertToDtoList(item.getType()));
        });

        return Optional.of(pokemonAbbreviatedDto);
    }

    @SuppressWarnings("Duplicates")
    private Optional<Pokemon> convertAbbreviatedToDomain(Optional<PokemonAbbreviatedDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        Pokemon pokemonAbbreviated = Pokemon.builder().build();

        dto.ifPresent(item -> {
            pokemonAbbreviated.setNumber(item.getNumber());
            pokemonAbbreviated.setName(item.getName());
        });

        return Optional.of(pokemonAbbreviated);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonEvolutionDto> convertEvolutionToDto(Optional<PokemonEvolution> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonEvolutionDto pokemonEvolutionDto = PokemonEvolutionDto.builder().build();

        domain.ifPresent(item -> {
            pokemonEvolutionDto.setLevel(item.getLevel());
            this.convertAbbreviatedToDto(Optional.of(item.getPokemon())).ifPresent(pokemonEvolutionDto::setPokemon);
        });

        return Optional.of(pokemonEvolutionDto);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonEvolution> convertEvolutionToDomain(Optional<PokemonEvolutionDto> dto) {

        if (dto.isEmpty())
            return Optional.empty();

        PokemonEvolution pokemonEvolution = PokemonEvolution.builder().build();

        dto.ifPresent(item -> {
            pokemonEvolution.setLevel(item.getLevel());
            this.convertAbbreviatedToDomain(Optional.of(item.getPokemon())).ifPresent(pokemonEvolution::setPokemon);
        });

        return Optional.of(pokemonEvolution);
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonMoveDto> convertPokemonMoveToDto(List<PokemonMove> moves) {

        if (moves == null || moves.isEmpty())
            return Collections.emptyList();

        List<PokemonMoveDto> list = new ArrayList<>();

        moves.stream().filter(Objects::nonNull)
                .forEach(item -> list.add(PokemonMoveDto.builder()
                        .levelLearn(item.getLevelLearn())
                        .move(convertMove.convertToDto(Optional.ofNullable(item.getMove()))
                                .orElseGet(MoveDto::new))
                        .build()));

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonMove> convertPokemonMoveToDomain(List<PokemonMoveDto> moves) {

        if (moves == null || moves.isEmpty())
            return Collections.emptyList();

        List<PokemonMove> list = new ArrayList<>();

        moves.stream().filter(Objects::nonNull)
                .forEach(item -> list.add(PokemonMove.builder()
                        .levelLearn(item.getLevelLearn())
                        .move(convertMove.convertToDomain(Optional.ofNullable(item.getMove()))
                                .orElseGet(Move::new))
                        .build()));

        return list;
    }
}

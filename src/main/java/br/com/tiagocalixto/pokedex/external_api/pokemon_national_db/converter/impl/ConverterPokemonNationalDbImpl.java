package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.converter.impl;

import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.converter.ConverterNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.AbilityNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.MoveNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.TypeNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.evolution_chain.EvolutionChainEvolveToNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.evolution_chain.EvolutionChainEvolvedFromNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.pokemon.PokemonNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.pokemon.PokemonStatsNationalDb;
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

import static br.com.tiagocalixto.pokedex.infra.util.Constant.EMPTY;

@Component
public class ConverterPokemonNationalDbImpl implements ConverterNationalDb<PokemonNationalDb, Pokemon> {

    private ConverterNationalDb<AbilityNationalDb, Ability> abilityConverter;
    private ConverterNationalDb<MoveNationalDb, Move> moveConverter;
    private ConverterNationalDb<TypeNationalDb, Type> typeConverter;

    @Autowired
    public ConverterPokemonNationalDbImpl(ConverterNationalDb<AbilityNationalDb, Ability> abilityConverter,
                                          ConverterNationalDb<MoveNationalDb, Move> moveConverter,
                                          ConverterNationalDb<TypeNationalDb, Type> typeConverter) {

        this.abilityConverter = abilityConverter;
        this.moveConverter = moveConverter;
        this.typeConverter = typeConverter;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Pokemon> convertToDomain(Optional<PokemonNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        Pokemon pokemon = Pokemon.builder().build();

        nationalDbEntity.ifPresent(item -> {
            pokemon.setId(0L);
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
    private Optional<PokemonStats> convertStats(Optional<PokemonStatsNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        PokemonStats stats = PokemonStats.builder().build();

        nationalDbEntity.ifPresent(item -> {
            stats.setSpeed(item.getSpeed());
            stats.setSpecialDefense(item.getSpecialDefense());
            stats.setSpecialAttack(item.getSpecialAttack());
            stats.setHp(item.getHp());
            stats.setDefense(item.getDefense());
            stats.setAttack(item.getAttack());
        });

        return Optional.of(stats);
    }

    private Optional<PokemonMove> convertMove(Optional<MoveNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        PokemonMove pokemonMove = PokemonMove.builder().build();

        nationalDbEntity.ifPresent(item -> {
            pokemonMove.setLevelLearn(item.getLevelLearn());
            this.moveConverter.convertToDomain(Optional.ofNullable(item)).ifPresent(pokemonMove::setMove);
        });

        return Optional.of(pokemonMove);
    }

    private List<PokemonMove> convertPokemonMove(List<MoveNationalDb> moves) {

        if(moves == null)
            return Collections.emptyList();

        return moves.stream()
                .map(item -> convertMove(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<PokemonEvolution> convertPokemonEvolvedFrom(
            Optional<EvolutionChainEvolvedFromNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        PokemonEvolution pokemonEvolution = PokemonEvolution.builder().build();

        nationalDbEntity.ifPresent(item ->
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

    private Optional<PokemonEvolution> convertPokemonEvolveTo(Optional<EvolutionChainEvolveToNationalDb> nationalDbEntity) {

        if (nationalDbEntity.isEmpty())
            return Optional.empty();

        PokemonEvolution pokemonEvolution = PokemonEvolution.builder().build();

        nationalDbEntity.ifPresent(item ->
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

    private List<PokemonEvolution> convertPokemonEvolveToList(List<EvolutionChainEvolveToNationalDb> listEvolveTo) {

        if(listEvolveTo == null)
            return Collections.emptyList();

        return listEvolveTo.stream()
                .map(item -> this.convertPokemonEvolveTo(Optional.ofNullable(item)).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

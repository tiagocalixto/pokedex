package br.com.tiagocalixto.pokedex.data_source.postgresql.converter.impl;

import br.com.tiagocalixto.pokedex.data_source.postgresql.converter.ConverterEntitySql;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonAbilityPk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonEvolutionPk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonMovePk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonTypePk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.*;
import br.com.tiagocalixto.pokedex.domain.Ability;
import br.com.tiagocalixto.pokedex.domain.Move;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionStoneEnum;
import br.com.tiagocalixto.pokedex.domain.enums.EvolutionTriggerEnum;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonEvolution;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonMove;
import br.com.tiagocalixto.pokedex.domain.pokemon.PokemonStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("pokemonConverterEntity")
public class ConverterPokemonEntityImpl implements ConverterEntitySql<PokemonEntity, Pokemon> {

    private ConverterEntitySql<TypeEntity, Type> convertType;
    private ConverterEntitySql<AbilityEntity, Ability> convertAbility;
    private ConverterEntitySql<MoveEntity, Move> convertMove;
    private ConverterEntitySql<EvolutionTriggerEntity, EvolutionTriggerEnum> convertTrigger;
    private ConverterEntitySql<EvolutionStoneEntity, EvolutionStoneEnum> convertItem;

    @Autowired
    public ConverterPokemonEntityImpl(ConverterEntitySql<TypeEntity, Type> convertType,
                                      ConverterEntitySql<AbilityEntity, Ability> convertAbility,
                                      ConverterEntitySql<MoveEntity, Move> convertMove,
                                      ConverterEntitySql<EvolutionTriggerEntity, EvolutionTriggerEnum> convertTrigger,
                                      ConverterEntitySql<EvolutionStoneEntity, EvolutionStoneEnum> convertItem) {

        this.convertType = convertType;
        this.convertAbility = convertAbility;
        this.convertMove = convertMove;
        this.convertTrigger = convertTrigger;
        this.convertItem = convertItem;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public Optional<PokemonEntity> convertToEntity(Optional<Pokemon> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonEntity pokemonEntity = PokemonEntity.builder().build();

        domain.ifPresent(item -> { //todo 4 - convert object managed by hibernate - using find by
            pokemonEntity.setId(item.getId());
            pokemonEntity.setName(item.getName());
            pokemonEntity.setNumber(item.getNumber());
            pokemonEntity.setWeight(item.getWeight());
            pokemonEntity.setHeight(item.getHeight());
            pokemonEntity.setAbout(item.getAbout());
            pokemonEntity.setUrlPicture(item.getUrlPicture());
            this.convertStatsToEntity(Optional.ofNullable(item.getStats()), item.getId()).ifPresent(pokemonEntity::setStats);
            pokemonEntity.setType(this.convertTypeToEntity(item.getType(), item.getId()));
            pokemonEntity.setEvolveTo(this.convertEvolveToToEntity(item.getEvolveTo(), item.getId()));
            this.convertEvolvedFromToEntity(Optional.ofNullable(item.getEvolvedFrom()), item.getId())
                    .ifPresent(pokemonEntity::setEvolvedFrom);
            pokemonEntity.setAbility(this.convertAbilityToEntity(item.getAbility(), item.getId()));
            pokemonEntity.setMove(this.convertMoveToEntity(item.getMove(), item.getId()));
            pokemonEntity.setWeakness(this.convertWeaknessesToEntity(item.getWeakness(), item.getId()));
        });

        setPokemonEntityOnRelations(pokemonEntity);

        return Optional.of(pokemonEntity);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<Pokemon> convertToDomain(Optional<PokemonEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Pokemon pokemon = Pokemon.builder().build();

        entity.ifPresent(item -> {
            pokemon.setId(item.getId());
            pokemon.setName(item.getName());
            pokemon.setNumber(item.getNumber());
            pokemon.setWeight(item.getWeight());
            pokemon.setHeight(item.getHeight());
            pokemon.setAbout(item.getAbout());
            pokemon.setUrlPicture(item.getUrlPicture());
            this.convertStatsToDomain(Optional.ofNullable(item.getStats())).ifPresent(pokemon::setStats);
            pokemon.setType(this.convertTypeToDomain(item.getType()));
            pokemon.setEvolveTo(this.convertEvolveToToDomain(item.getEvolveTo()));
            this.convertEvolvedFromToDomain(Optional.ofNullable(item.getEvolvedFrom()))
                    .ifPresent(pokemon::setEvolvedFrom);
            pokemon.setAbility(this.convertAbilityToDomain(item.getAbility()));
            pokemon.setMove(this.convertMoveToDomain(item.getMove()));
            pokemon.setWeakness(this.convertWeaknessesToDomain(item.getWeakness()));
        });

        return Optional.of(pokemon);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonStatsEntity> convertStatsToEntity(Optional<PokemonStats> domain, long idPokemon) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonStatsEntity pokemonStatsEntity = PokemonStatsEntity.builder().build();

        domain.ifPresent(item -> {
            pokemonStatsEntity.setId(idPokemon);
            pokemonStatsEntity.setAttack(item.getAttack());
            pokemonStatsEntity.setDefense(item.getDefense());
            pokemonStatsEntity.setHp(item.getHp());
            pokemonStatsEntity.setSpecialAttack(item.getSpecialAttack());
            pokemonStatsEntity.setSpecialDefense(item.getSpecialDefense());
            pokemonStatsEntity.setSpeed(item.getSpeed());
        });

        return Optional.of(pokemonStatsEntity);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonStats> convertStatsToDomain(Optional<PokemonStatsEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        PokemonStats pokemonStats = PokemonStats.builder().build();

        entity.ifPresent(item -> {
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
    private Optional<PokemonEntity> convertAbbreviatedToEntity(Optional<Pokemon> domain) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonEntity pokemonEntity = PokemonEntity.builder().build();

        domain.ifPresent(item -> {
            pokemonEntity.setId(item.getId());
            pokemonEntity.setName(item.getName());
            pokemonEntity.setNumber(item.getNumber());
            pokemonEntity.setUrlPicture(item.getUrlPicture());
            pokemonEntity.setType(this.convertTypeToEntity(item.getType(), item.getId()));
        });

        return Optional.of(pokemonEntity);
    }

    @SuppressWarnings("Duplicates")
    private Optional<Pokemon> convertAbbreviatedToDomain(Optional<PokemonEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        Pokemon pokemonAbbreviated = Pokemon.builder().build();

        entity.ifPresent(item -> {
            pokemonAbbreviated.setId(item.getId());
            pokemonAbbreviated.setName(item.getName());
            pokemonAbbreviated.setNumber(item.getNumber());
            pokemonAbbreviated.setUrlPicture(item.getUrlPicture());
            pokemonAbbreviated.setType(this.convertTypeToDomain(item.getType()));
        });

        return Optional.of(pokemonAbbreviated);
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonTypeEntity> convertTypeToEntity(List<Type> domain, Long idPokemon) {

        if (domain == null || domain.isEmpty())
            return Collections.emptyList();

        List<PokemonTypeEntity> list = new ArrayList<>();

        domain.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    PokemonTypeEntity pokemonTypeEntity = PokemonTypeEntity.builder().build();
                    convertType.convertToEntity(Optional.ofNullable(item)).ifPresent(pokemonTypeEntity::setType);
                    pokemonTypeEntity.setId(
                            PokemonTypePk.builder()
                                    .idPokemonFk(idPokemon)
                                    .idTypeFk(pokemonTypeEntity.getType().getId())
                                    .build());
                    list.add(pokemonTypeEntity);
                });

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<Type> convertTypeToDomain(List<PokemonTypeEntity> entity) {

        if (entity == null || entity.isEmpty())
            return Collections.emptyList();

        List<Type> list = new ArrayList<>();

        entity.stream().filter(Objects::nonNull)
                .forEach(item -> convertType.convertToDomain(Optional.ofNullable(item.getType()))
                        .ifPresent(list::add));

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonEvolutionEntity> convertEvolveToToEntity(List<PokemonEvolution> domain,
                                                                 Long idEvolution) {

        if (domain == null || domain.isEmpty())
            return Collections.emptyList();

        return domain.stream()
                .map(item -> PokemonEvolutionEntity.builder()
                        .id(PokemonEvolutionPk.builder()
                                .idPokemonFk(idEvolution)
                                .idEvolutionFk(item.getPokemon().getId())
                                .build())
                        .evolution(this.convertAbbreviatedToEntity(Optional.ofNullable(item.getPokemon()))
                                .orElse(PokemonEntity.builder().build()))
                        .evolutionTrigger(this.convertTrigger.convertToEntity(Optional.ofNullable(item.getTrigger()))
                                .orElse(EvolutionTriggerEntity.builder().build()))
                        .level(item.getLevel())
                        .evolutionItem(this.convertItem.convertToEntity(Optional.ofNullable(item.getItem()))
                                .orElse(null))
                        .build())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonEvolution> convertEvolveToToDomain(List<PokemonEvolutionEntity> entity) {

        if (entity == null || entity.isEmpty())
            return Collections.emptyList();

        return entity.stream()
                .map(item -> PokemonEvolution.builder()
                        .pokemon(this.convertAbbreviatedToDomain(Optional.ofNullable(item.getEvolution()))
                                .orElse(Pokemon.builder().build()))
                        .trigger(this.convertTrigger.convertToDomain(Optional.ofNullable(item.getEvolutionTrigger()))
                                .orElse(EvolutionTriggerEnum.LEVEL_UP))
                        .item(this.convertItem.convertToDomain(Optional.ofNullable(item.getEvolutionItem()))
                                .orElse(null))
                        .level(item.getLevel())
                        .build())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonEvolutionEntity> convertEvolvedFromToEntity(Optional<PokemonEvolution> domain,
                                                                        Long idPokemon) {

        if (domain.isEmpty())
            return Optional.empty();

        PokemonEvolutionEntity pokemonEvolutionEntity = PokemonEvolutionEntity.builder().build();

        domain.ifPresent(item -> {
            pokemonEvolutionEntity.setId(
                    PokemonEvolutionPk.builder()
                            .idPokemonFk(item.getPokemon().getId())
                            .idEvolutionFk(idPokemon)
                            .build());
            this.convertAbbreviatedToEntity(Optional.ofNullable(item.getPokemon()))
                    .ifPresent(pokemonEvolutionEntity::setPokemon);
            pokemonEvolutionEntity.setEvolutionTrigger(this.convertTrigger
                    .convertToEntity(Optional.ofNullable(item.getTrigger()))
                    .orElse(EvolutionTriggerEntity.builder().build()));
            pokemonEvolutionEntity.setLevel(item.getLevel());
            pokemonEvolutionEntity.setEvolutionItem(this.convertItem
                    .convertToEntity(Optional.ofNullable(item.getItem())).orElse(null));
        });

        return Optional.of(pokemonEvolutionEntity);
    }

    @SuppressWarnings("Duplicates")
    private Optional<PokemonEvolution> convertEvolvedFromToDomain(Optional<PokemonEvolutionEntity> entity) {

        if (entity.isEmpty())
            return Optional.empty();

        PokemonEvolution pokemonEvolution = PokemonEvolution.builder().build();

        entity.ifPresent(item -> {
            this.convertAbbreviatedToDomain(Optional.ofNullable(item.getPokemon())).ifPresent(pokemonEvolution::setPokemon);
            pokemonEvolution.setTrigger(this.convertTrigger.convertToDomain(Optional.ofNullable(item.getEvolutionTrigger()))
                    .orElse(EvolutionTriggerEnum.LEVEL_UP));
            pokemonEvolution.setLevel(item.getLevel());
            pokemonEvolution.setItem(this.convertItem.convertToDomain(Optional.ofNullable(item.getEvolutionItem()))
                    .orElse(null));
        });

        return Optional.of(pokemonEvolution);
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonAbilityEntity> convertAbilityToEntity(List<Ability> domain, Long idPokemon) {

        if (domain == null || domain.isEmpty())
            return Collections.emptyList();

        List<PokemonAbilityEntity> list = new ArrayList<>();

        domain.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    PokemonAbilityEntity pokemonAbilityEntity = PokemonAbilityEntity.builder().build();
                    convertAbility.convertToEntity(Optional.ofNullable(item)).ifPresent(pokemonAbilityEntity::setAbility);
                    pokemonAbilityEntity.setId(
                            PokemonAbilityPk.builder()
                                    .idPokemonFk(idPokemon)
                                    .idAbilityFk(pokemonAbilityEntity.getAbility().getId())
                                    .build());
                    list.add(pokemonAbilityEntity);
                });

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<Ability> convertAbilityToDomain(List<PokemonAbilityEntity> entity) {

        if (entity == null || entity.isEmpty())
            return Collections.emptyList();

        List<Ability> list = new ArrayList<>();

        entity.stream().filter(Objects::nonNull)
                .forEach(item -> convertAbility.convertToDomain(Optional.ofNullable(item.getAbility()))
                        .ifPresent(list::add));

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonMoveEntity> convertMoveToEntity(List<PokemonMove> domain, Long idPokemon) {

        if (domain == null || domain.isEmpty())
            return Collections.emptyList();

        List<PokemonMoveEntity> list = new ArrayList<>();

        domain.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    PokemonMoveEntity pokemonMoveEntity = PokemonMoveEntity.builder().build();
                    convertMove.convertToEntity(Optional.ofNullable(item.getMove())).ifPresent(pokemonMoveEntity::setMove);
                    pokemonMoveEntity.setId(
                            PokemonMovePk.builder()
                                    .idPokemonFk(idPokemon)
                                    .idMoveFk(pokemonMoveEntity.getMove().getId())
                                    .build());
                    pokemonMoveEntity.setLevelLearn(item.getLevelLearn());
                    list.add(pokemonMoveEntity);
                });

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonMove> convertMoveToDomain(List<PokemonMoveEntity> entity) {

        if (entity == null || entity.isEmpty())
            return Collections.emptyList();

        List<PokemonMove> list = new ArrayList<>();

        entity.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    PokemonMove pokemonMove = PokemonMove.builder().build();
                    pokemonMove.setLevelLearn(item.getLevelLearn());
                    convertMove.convertToDomain(Optional.ofNullable(item.getMove())).ifPresent(pokemonMove::setMove);
                    list.add(pokemonMove);
                });

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<PokemonWeaknessesEntity> convertWeaknessesToEntity(List<Type> domain, Long idPokemon) {

        if (domain == null || domain.isEmpty())
            return Collections.emptyList();

        List<PokemonWeaknessesEntity> list = new ArrayList<>();

        domain.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    PokemonWeaknessesEntity pokemonWeaknessesEntity = PokemonWeaknessesEntity.builder().build();
                    convertType.convertToEntity(Optional.ofNullable(item)).ifPresent(pokemonWeaknessesEntity::setType);
                    pokemonWeaknessesEntity.setId(
                            PokemonTypePk.builder()
                                    .idPokemonFk(idPokemon)
                                    .idTypeFk(pokemonWeaknessesEntity.getType().getId())
                                    .build());
                    list.add(pokemonWeaknessesEntity);
                });

        return list;
    }

    @SuppressWarnings("Duplicates")
    private List<Type> convertWeaknessesToDomain(List<PokemonWeaknessesEntity> entity) {

        if (entity == null || entity.isEmpty())
            return Collections.emptyList();

        List<Type> list = new ArrayList<>();

        entity.stream().filter(Objects::nonNull)
                .forEach(item -> convertType.convertToDomain(Optional.ofNullable(item.getType())).ifPresent(list::add));

        return list;
    }

    private void setPokemonEntityOnRelations(PokemonEntity pokemonEntity) {

        PokemonEntity entity = getEntityAbbreviated(pokemonEntity);

        pokemonEntity.getStats().setPokemon(entity);
        pokemonEntity.getType().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(entity));
        pokemonEntity.getAbility().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(entity));
        pokemonEntity.getMove().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(entity));
        pokemonEntity.getWeakness().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(entity));
        pokemonEntity.getEvolveTo().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(entity));

        if (pokemonEntity.getEvolvedFrom() != null)
            pokemonEntity.getEvolvedFrom().setEvolution(entity);
    }

    private PokemonEntity getEntityAbbreviated(PokemonEntity pokemonEntity) {

        return PokemonEntity.builder()
                .id(pokemonEntity.getId())
                .name(pokemonEntity.getName())
                .number(pokemonEntity.getNumber())
                .weight(pokemonEntity.getWeight())
                .height(pokemonEntity.getHeight())
                .about(pokemonEntity.getAbout())
                .urlPicture(pokemonEntity.getUrlPicture())
                .evolveTo(Collections.emptyList())
                .type(Collections.emptyList())
                .ability(Collections.emptyList())
                .move(Collections.emptyList())
                .weakness(Collections.emptyList())
                .build();
    }
}

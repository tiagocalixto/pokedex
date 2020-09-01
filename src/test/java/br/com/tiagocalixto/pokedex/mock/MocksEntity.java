package br.com.tiagocalixto.pokedex.mock;

import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.AuditCollectionMongo;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.*;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonAbilityPk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonEvolutionPk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonMovePk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonTypePk;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.*;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("Duplicates")
public class MocksEntity {

    private MocksEntity() {
    }


    public static PokemonEntity createPokemon() {

        Long id = new RandomDataGenerator().nextLong(1, 1000);
        return createPokemon(id);
    }

    public static PokemonEntity createPokemon(Long id) {

        PokemonEntity pokemon = PokemonEntity.builder()
                .id(id)
                .name("Kadabra")
                .number(64L)
                .weight(56L)
                .height(BigDecimal.valueOf(1.3))
                .about("It stares at a silver spoon to amplify its psychic powers before it lets loose. " +
                        "Apparently, gold spoons are no good.")
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/064.png")
                .stats(createStats(id))
                .type(List.of(createPokemonType(id, createType())))
                .evolvedFrom(List.of(createPokemonEvolvedFrom(createEvolvedFromPokemon(), id)))
                .evolveTo(List.of(createPokemonEvolveTo(createEvolveToPokemon(), id)))
                .move(List.of(createPokemonMove(createMove(), id)))
                .ability(List.of(createPokemonAbility(createAbility(), id)))
                .weakness(List.of(createPokemonWeakness(createWeakness(), id)))
                .build();

        return setPokemonEntityOnRelations(pokemon);
    }

    public static PokemonStatsEntity createStats(Long id) {

        return PokemonStatsEntity.builder()
                .id(id)
                .attack(35L)
                .defense(30L)
                .hp(40L)
                .speed(105L)
                .specialAttack(120L)
                .specialDefense(70L)
                .build();
    }

    public static PokemonTypeEntity createPokemonType(Long idPokemon, TypeEntity type) {

        return PokemonTypeEntity.builder()
                .id(PokemonTypePk.builder()
                        .idPokemonFk(idPokemon)
                        .idTypeFk(type.getId())
                        .build())
                .type(type)
                .build();
    }

    public static TypeEntity createType() {

        return TypeEntity.builder()
                .description("PSYCHIC")
                .id(new RandomDataGenerator().nextLong(1L, 1000L))
                .build();
    }

    public static PokemonEvolutionEntity createPokemonEvolveTo(PokemonEntity evolveTo, Long idPokemon) {

        return PokemonEvolutionEntity.builder()
                .id(PokemonEvolutionPk.builder()
                        .idEvolutionFk(evolveTo.getId())
                        .idPokemonFk(idPokemon)
                        .build())
                .evolutionTrigger(createEvolutionTrigger())
                .evolution(evolveTo)
                .build();
    }

    public static PokemonEvolutionEntity createPokemonEvolvedFrom(PokemonEntity evolvedFrom, Long idPokemon) {

        return PokemonEvolutionEntity.builder()
                .id(PokemonEvolutionPk.builder()
                        .idEvolutionFk(idPokemon)
                        .idPokemonFk(evolvedFrom.getId())
                        .build())
                .evolutionTrigger(createEvolutionTrigger())
                .pokemon(evolvedFrom)
                .level(16L)
                .build();
    }

    public static EvolutionStoneEntity createEvolutionStone() {

        return EvolutionStoneEntity.builder()
                .description("FIRE_STONE")
                .build();
    }

    public static EvolutionTriggerEntity createEvolutionTrigger() {

        return EvolutionTriggerEntity.builder()
                .description("LEVEL_UP")
                .build();
    }

    public static PokemonEntity createEvolvedFromPokemon() {

        Long id = new RandomDataGenerator().nextLong(1L, 1000L);

        return PokemonEntity.builder()
                .id(id)
                .name("Abra")
                .number(63L)
                .weight(20L)
                .height(BigDecimal.valueOf(1))
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/063.png")
                .about("abra comes before kadabra")
                .stats(createStats(id))
                .type(List.of(createPokemonType(id, createType())))
                .evolvedFrom(Collections.emptyList())
                .evolveTo(Collections.emptyList())
                .ability(Collections.emptyList())
                .move(Collections.emptyList())
                .ability(Collections.emptyList())
                .weakness(Collections.emptyList())
                .build();
    }

    public static PokemonEntity createEvolveToPokemon() {

        Long id = new RandomDataGenerator().nextLong(1L, 1000L);

        return PokemonEntity.builder()
                .id(id)
                .name("Alakazam")
                .number(65L)
                .weight(20L)
                .height(BigDecimal.valueOf(1))
                .urlPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/065.png")
                .about("Alakazam is the final evolution of this guys")
                .stats(createStats(id))
                .type(List.of(createPokemonType(id, createType())))
                .evolvedFrom(Collections.emptyList())
                .evolveTo(Collections.emptyList())
                .ability(Collections.emptyList())
                .move(Collections.emptyList())
                .ability(Collections.emptyList())
                .weakness(Collections.emptyList())
                .build();
    }

    public static PokemonMoveEntity createPokemonMove(MoveEntity move, Long idPokemon) {

        return PokemonMoveEntity.builder()
                .id(PokemonMovePk.builder()
                        .idMoveFk(move.getId())
                        .idPokemonFk(idPokemon)
                        .build())
                .levelLearn(18L)
                .move(move)
                .build();
    }

    public static MoveEntity createMove() {

        return MoveEntity.builder()
                .about("Inflicts regular damage. Has a 10% chance to confuse the target.")
                .description("Confusion")
                .type(createType())
                .accuracy(BigDecimal.valueOf(100))
                .power(50L)
                .pp(25L)
                .id(new RandomDataGenerator().nextLong(1, 1000))
                .build();
    }

    public static PokemonAbilityEntity createPokemonAbility(AbilityEntity ability, Long idPokemon) {

        return PokemonAbilityEntity.builder()
                .id(PokemonAbilityPk.builder()
                        .idAbilityFk(ability.getId())
                        .idPokemonFk(idPokemon)
                        .build())
                .ability(ability)
                .build();
    }

    public static AbilityEntity createAbility() {

        return AbilityEntity.builder()
                .description("Magic-guard")
                .id(new RandomDataGenerator().nextLong(1, 1000))
                .build();
    }

    public static PokemonWeaknessesEntity createPokemonWeakness(TypeEntity weakness, Long idPokemon) {

        return PokemonWeaknessesEntity.builder()
                .id(PokemonTypePk.builder()
                        .idTypeFk(weakness.getId())
                        .idPokemonFk(idPokemon)
                        .build())
                .type(weakness)
                .build();
    }

    public static TypeEntity createWeakness() {

        return TypeEntity.builder()
                .description("GHOST")
                .id(new RandomDataGenerator().nextLong(1L, 1000L))
                .build();
    }

    private static PokemonEntity setPokemonEntityOnRelations(PokemonEntity pokemonEntity) {

        pokemonEntity.getStats().setPokemon(pokemonEntity);
        pokemonEntity.getType().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(pokemonEntity));
        pokemonEntity.getAbility().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(pokemonEntity));
        pokemonEntity.getMove().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(pokemonEntity));
        pokemonEntity.getWeakness().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(pokemonEntity));
        pokemonEntity.getEvolveTo().stream().filter(Objects::nonNull).forEach(item -> item.setPokemon(pokemonEntity));
        pokemonEntity.getEvolvedFrom().stream().filter(Objects::nonNull).forEach(item -> item.setEvolution(pokemonEntity));

        return pokemonEntity;
    }

    public static AuditCollectionMongo createAuditCollection(){

        Pokemon pokemon = MocksDomain.createPokemon();

        return AuditCollectionMongo.builder()
                .id(String.valueOf(new RandomDataGenerator().nextLong(1,1000)))
                .date(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .date(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .entityName(pokemon.getClass().getSimpleName())
                .idEntity(pokemon.getId().toString())
                .version(String.valueOf(new RandomDataGenerator().nextLong(1,1000)))
                .entity((Object) pokemon)
                .build();
    }
}

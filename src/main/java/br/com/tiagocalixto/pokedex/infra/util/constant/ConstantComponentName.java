package br.com.tiagocalixto.pokedex.infra.util.constant;

public final class ConstantComponentName {

    private ConstantComponentName() {}


    //USE CASE
    public static final String POKEMON_DELETE_USE_CASE = "PokemonDeleteByIdUseCase";
    public static final String POKEMON_FIND_ALL_BY_NAME_USE_CASE = "PokemonFindAllByNameUseCase";
    public static final String POKEMON_FIND_ONE_BY_ID_USE_CASE = "PokemonFindOneByIdUseCase";
    public static final String POKEMON_FIND_PAGEABLE_USE_CASE = "PokemonFindPageableUseCase";
    public static final String POKEMON_SAVE_USE_CASE = "PokemonSaveUseCase";
    public static final String POKEMON_UPDATE_USE_CASE = "PokemonUpdateUseCase";
    public static final String POKEMON_EVOLUTION_ABSTRACT_USE_CASE = "EvolutionAbstractUseCase";
    public static final String POKEMON_FIND_ONE_BY_NUMBER_CASE = "PokemonFindByNumberUseCase";
    public static final String POKEMON_EXISTS_BY_NUMBER_USE_CASE = "PokemonExistsByNumberUseCase";
    public static final String POKEMON_PERSIST_ABSTRACT = "PokemonPersistAbstractUseCase";
    public static final String POKEMON_ASSOCIATE_EVOLVED_FROM_USE_CASE = "EvolutionAssociateOrInsertEvolvedFromUseCase";
    public static final String POKEMON_ASSOCIATE_EVOLVE_TO_USE_CASE = "EvolutionAssociateOrInsertEvolveToUseCase";

    //INTEGRATION
    public static final String INTEGRATION_NATIONAL_DEX = "IntegrationNationalDex";

    //REPOSITORY
    public static final String POKEMON_REPOSITORY_SQL = "PokemonRepositorySql";

    //AUDIT
    public static final String POKEMON_AUDIT = "PokemonAuditMongo";
}

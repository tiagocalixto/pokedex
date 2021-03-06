package br.com.tiagocalixto.pokedex.infra.constant;

public final class Constant {

    private Constant(){}


    public static final String INVALID_ABBREVIATED_GENERIC = "Invalid pokemon abbreviated!";
    public static final String INVALID_POKEMON_GENERIC = "Invalid pokemon!";
    public static final String INVALID_NAME_OR_NUMBER = "Number between 1 and 151 and/or pokemon name is required!";
    public static final String NAME_INVALID_SIZE = "Name Length must be between 3 and 50 chars!";
    public static final String NAME_IS_REQUIRED = "Name is required!";
    public static final String NAME_IS_INVALID = "Name is invalid. Field accept's only Letters!";
    public static final String NUMBER_IS_REQUIRED = "Number can't be null or empty!";
    public static final String NUMBER_INVALID_RANGE = "Number must be between 1 and 151!";
    public static final String WEIGHT_IS_REQUIRED = "Weight is required!";
    public static final String WEIGHT_INVALID_RANGE = "Weight must be between 1 and 1000 kg!";
    public static final String HEIGHT_IS_REQUIRED = "Height is required!";
    public static final String HEIGHT_INVALID_RANGE = "Height must be between 0.1 and 25 mts!";
    public static final String ABOUT_ESPECIAL_CHAR = "About pattern invalid, the field can't receive especial chars!";
    public static final String ABOUT_LENGTH_INVALID = "About length invalid. It must contain between 3 and 255 chars!";
    public static final String URL_PICTURE_IS_REQUIRED = "Url picture is required!";
    public static final String URL_PICTURE_INVALID_FORMAT = "Url picture informed is a invalid url!";
    public static final String STATS_IS_REQUIRED = "Pokemon stats is required!";
    public static final String HP_IS_REQUIRED = "Stats hp is required!";
    public static final String ATTACK_IS_REQUIRED = "Stats attack is required!";
    public static final String DEFENSE_IS_REQUIRED = "Stats defense is required!";
    public static final String SPECIAL_ATTACK_IS_REQUIRED = "Stats special attack is required!";
    public static final String SPECIAL_DEFENSE_IS_REQUIRED = "Stats special defense is required!";
    public static final String SPEED_IS_REQUIRED = "Stats speed is required!";
    public static final String TYPE_IS_REQUIRED = "Type is required!";
    public static final String DUPLICATED_ITEM_TYPE = "Detected duplicated item in type list!";
    public static final String DESCRIPTION_FIELD_IS_INVALID = "The Field description is invalid. Field accept's only Letters!";
    public static final String DESCRIPTION_FIELD_IS_REQUIRED = "Description is required!";
    public static final String DESCRIPTION_LENGTH_INVALID = "Description length invalid. It must contain between 3 and 50 chars!";
    public static final String PP_REQUIRED = "PP is Required!";
    public static final String POWER_REQUIRED = "Power is Required!";
    public static final String ACCURACY_REQUIRED = "Accuracy is Required!";
    public static final String ACCURACY_INVALID_RANGE = "Accuracy must be between 0.1 and 100";
    public static final String INVALID_RANGE_1_1000 = "Value must be between 1 and 1000!";
    public static final String LEVEL_IS_REQUIRED = "Level is required!";
    public static final String EVOLUTION_LEVEL_IS_REQUIRED = "For associate_or_insert type Level Up, field level is required!";
    public static final String EVOLUTION_ITEM_IS_REQUIRED = "For associate_or_insert type Item, field item is required!";
    public static final String INVALID_LEVEL_RANGE = "Level must be between 1 and 100!";
    public static final String POKEMON_IS_REQUIRED = "Pokemon is required!";
    public static final String EVOLUTION_TRIGGER_REQUIRED = "Evolution trigger is required!";
    public static final String EVOLUTION_GENERIC= "Pokemon Evolution contains incorrect info!";
    public static final String MOVE_IS_REQUIRED = "Move is required!";
    public static final String MOVE_IS_DUPLICATE = "Duplicated item detected on move list!";
    public static final String DUPLICATED_ITEM_ABILITY = "Detected duplicated item in ability list!";
    public static final String DUPLICATED_ITEM_WEAKNESS = "Detected duplicated item in weakness list!";
    public static final String DUPLICATED_ITEM_GENERIC = "Has a duplicated item in this list!";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String SPEED = "speed";
    public static final String HP = "hp";
    public static final String SPECIAL_DEFENSE = "special-defense";
    public static final String SPECIAL_ATTACK = "special-attack";
    public static final String EMPTY = "";
    public static final String POKEMON_NOT_FOUND_BY_NUMBER = "No Pokemon found for number = ";
    public static final String POKEMON_NOT_FOUND_BY_ID = "No Pokemon found for id = ";
    public static final String POKEMON_NOT_FOUND_BY_NAME = "No pokemon found by name, Name = ";
    public static final String POKEMON_ALREADY_EXISTS = "Pokemon already exists, number = ";
    public static final String NUMBER = "number";
    public static final String WEAKNESS_CANT_BE_TYPE = "Weakness can't be equal type!";
    public static final String EVOLVE_TO_DUPLICATED = "Duplicated item detected on evolve to list!";
    public static final String NATIONAL_DEX_UNAVAILABLE = "Pokemon National Dex is unavailable, try again in a few minutes!";
    public static final String POKEMON_INCORRECT_NAME = "The pokemon name provided, don't belongs to pokemon number provided!";
    public static final String POKEMON_INCORRECT_TYPE = "Follow type(s) don't belongs to pokemon";
    public static final String POKEMON_INCORRECT_MOVE = "Follow move(s) don't belongs to pokemon";
    public static final String POKEMON_INCORRECT_EVOLVED_FROM = "Pokemon evolved from is incorrect!";
    public static final String POKEMON_DONT_EVOLVED_FROM = "This pokemon dont evolves from any other!";
    public static final String POKEMON_CONSIDER = "This api is old school, considers only first generation pokemon (1-151)";
    public static final String POKEMON_DONT_EVOLVES_TO = "This pokemon don't evolves to any other!";
    public static final String POKEMON_INCORRECT_EVOLVE_TO = "Pokemon evolve to is incorrect!";
    public static final String POKEMON_EVOLVED_FROM_HIMSELF = "Pokemon can't evolved from himself!";
    public static final String POKEMON_EVOLVES_TO_HIMSELF = "Pokemon can't evolve into himself!";
    public static final String PAGE_HAS_NO_CONTENT = "Page has no content!";
    public static final String CANT_CHANGE_NAME_ON_UPDATE = "Can't change pokemon name on update!";
    public static final String CANT_CHANGE_NUMBER_ON_UPDATE = "Can't change pokemon number on update!";
    public static final String PAGE_NUMBER_TOO_SMALL = "Page number must be greater than 0!";
    public static final String ID_TOO_SMALL = "Id must be greater than 0!";
}


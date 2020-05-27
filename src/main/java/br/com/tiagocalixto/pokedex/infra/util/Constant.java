package br.com.tiagocalixto.pokedex.infra.util;

public final class Constant {

    private Constant(){}


    public static final String INVALID_ABBREVIATED_GENERIC = "Invalid pokemon abbreviated!";
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
    public static final String EVOLUTION_LEVEL_IS_REQUIRED = "For evolution type Level Up, field level is required!";
    public static final String EVOLUTION_ITEM_IS_REQUIRED = "For evolution type Item, field item is required!";
    public static final String INVALID_LEVEL_RANGE = "Level must be between 1 and 100!";
    public static final String POKEMON_IS_REQUIRED = "Pokemon is required!";
    public static final String EVOLUTION_TRIGGER_REQUIRED = "Evolution trigger is required!";
    public static final String EVOLUTION_GENERIC= "Pokemon Evolution contains incorrect info!";
    public static final String MOVE_IS_REQUIRED = "Move is required!";
    public static final String DUPLICATED_ITEM_MOVE = "Detected duplicated item in move list!";
    public static final String DUPLICATED_ITEM_ABILITY = "Detected duplicated item in ability list!";
    public static final String DUPLICATED_ITEM_WEAKNESS = "Detected duplicated item in weakness list!";
    public static final String DUPLICATED_ITEM_GENERIC = "Has a duplicated item in this list!";
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String SPEED = "speed";
    public static final String HP = "hp";
    public static final String SPECIAL_DEFENSE = "special-defense";
    public static final String SPECIAL_ATTACK = "special-attack";
    public static final String EMPTY = "";
    public static final Long FIRST_POKEMON_NUMBER = 1L;
    public static final Long LAST_POKEMON_NUMBER = 151L;
    public static final String POKEMON_NOT_FOUND_BY_NUMBER = "No Pokemon found by number, number = ";
    public static final String POKEMON_NOT_FOUND_BY_NAME = "No pokemon found by name, Name = ";
    public static final String NAME = "name";
}


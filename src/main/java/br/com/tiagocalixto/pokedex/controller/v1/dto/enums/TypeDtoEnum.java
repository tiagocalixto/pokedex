package br.com.tiagocalixto.pokedex.controller.v1.dto.enums;

public enum  TypeDtoEnum {

    GRASS("GRASS"),
    POISON("POISON"),
    FIRE("FIRE"),
    FLYING("FLYING"),
    WATER("WATER"),
    BUG("BUG"),
    NORMAL("NORMAL"),
    ELECTRIC("ELECTRIC"),
    GROUND("GROUND"),
    FAIRY("FAIRY"),
    FIGHTING("FIGHTING"),
    PSYCHIC("PSYCHIC"),
    ROCK("ROCK"),
    STEEL("STEEL"),
    ICE("ICE"),
    GHOST("GHOST"),
    DRAGON("DRAGON");


    private String description;

    TypeDtoEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

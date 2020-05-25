package br.com.tiagocalixto.pokedex.controller.dto.enums;

public enum EvolutionStoneDtoEnum {

    FIRE_STONE("FIRE_STONE"),
    WATER_STONE("WATER_STONE"),
    THUNDER_STONE("THUNDER_STONE"),
    LEAF_STONE("LEAF_STONE"),
    MOON_STONE("MOON_STONE");


    private String description;

    EvolutionStoneDtoEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

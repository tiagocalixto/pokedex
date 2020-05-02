package br.com.tiagocalixto.pokedex.controller.dto.enums;

public enum EvolutionStoneDtoEnum {

    FIRE_STONE("Fire stone"),
    WATER_STONE("Water stone"),
    THUNDER_STONE("Thunder stone"),
    LEAF_STONE("Leaf stone"),
    MOON_STONE("Moon stone");


    private String description;

    EvolutionStoneDtoEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

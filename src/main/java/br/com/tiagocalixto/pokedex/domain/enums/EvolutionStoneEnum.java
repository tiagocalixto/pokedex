package br.com.tiagocalixto.pokedex.domain.enums;

public enum EvolutionStoneEnum {

    FIRE_STONE("FIRE_STONE"),
    WATER_STONE("WATER_STONE"),
    THUNDER_STONE("THUNDER_STONE"),
    LEAF_STONE("LEAF_STONE"),
    MOON_STONE("MOON_STONE");


    private String description;

    EvolutionStoneEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

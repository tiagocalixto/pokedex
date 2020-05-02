package br.com.tiagocalixto.pokedex.domain.enums;

public enum EvolutionStoneEnum {

    FIRE_STONE("Fire stone"),
    WATER_STONE("Water stone"),
    THUNDER_STONE("Thunder stone"),
    LEAF_STONE("Leaf stone"),
    MOON_STONE("Moon stone");


    private String description;

    EvolutionStoneEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

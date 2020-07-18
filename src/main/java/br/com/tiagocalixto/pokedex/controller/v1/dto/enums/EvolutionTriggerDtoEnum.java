package br.com.tiagocalixto.pokedex.controller.v1.dto.enums;

public enum EvolutionTriggerDtoEnum {

    LEVEL_UP("LEVEL_UP"),
    TRADE("TRADE"),
    USE_ITEM("USE_ITEM");


    private String description;

    EvolutionTriggerDtoEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

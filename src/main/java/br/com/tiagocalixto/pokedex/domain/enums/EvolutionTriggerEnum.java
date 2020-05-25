package br.com.tiagocalixto.pokedex.domain.enums;

public enum  EvolutionTriggerEnum {

    LEVEL_UP("LEVEL_UP"),
    TRADE("TRADE"),
    USE_ITEM("USE_ITEM");


    private String description;

    EvolutionTriggerEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

package br.com.tiagocalixto.pokedex.domain.enums;

public enum  EvolutionTriggerEnum {

    LEVEL_UP("Level up"),
    TRADE("Trade"),
    USE_ITEM("Use item");


    private String description;

    EvolutionTriggerEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

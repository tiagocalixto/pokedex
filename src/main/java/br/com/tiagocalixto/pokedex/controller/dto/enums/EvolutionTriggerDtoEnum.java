package br.com.tiagocalixto.pokedex.controller.dto.enums;

public enum EvolutionTriggerDtoEnum {

    LEVEL_UP("Level up"),
    TRADE("Trade"),
    USE_ITEM("Use item");


    private String description;

    EvolutionTriggerDtoEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}

package br.com.tiagocalixto.pokedex.infra.exception;

public class PokemonMoveIncorrectException extends RuntimeException {

    public PokemonMoveIncorrectException(String message){ super(message);}
}

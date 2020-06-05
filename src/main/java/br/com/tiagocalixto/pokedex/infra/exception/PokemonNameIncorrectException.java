package br.com.tiagocalixto.pokedex.infra.exception;

public class PokemonNameIncorrectException extends RuntimeException {

    public PokemonNameIncorrectException(String message){ super(message);}
}

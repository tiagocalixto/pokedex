package br.com.tiagocalixto.pokedex.infra.exception;

public class PokemonIncorrectTypeException extends RuntimeException {

    public PokemonIncorrectTypeException(String message){ super(message);}
}

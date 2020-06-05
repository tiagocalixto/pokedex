package br.com.tiagocalixto.pokedex.infra.exception;

public class PokemonIncorretTypeException extends RuntimeException {

    public PokemonIncorretTypeException(String message){ super(message);}
}

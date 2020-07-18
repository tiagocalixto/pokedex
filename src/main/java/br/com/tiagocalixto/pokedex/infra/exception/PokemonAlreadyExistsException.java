package br.com.tiagocalixto.pokedex.infra.exception;

public class PokemonAlreadyExistsException extends RuntimeException {

    public PokemonAlreadyExistsException(String message){ super(message);}
}

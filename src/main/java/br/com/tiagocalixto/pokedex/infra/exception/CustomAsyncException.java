package br.com.tiagocalixto.pokedex.infra.exception;

public class CustomAsyncException extends RuntimeException {

    public CustomAsyncException(String message){ super(message);}
}

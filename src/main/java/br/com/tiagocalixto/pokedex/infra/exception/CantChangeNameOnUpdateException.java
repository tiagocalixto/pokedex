package br.com.tiagocalixto.pokedex.infra.exception;

public class CantChangeNameOnUpdateException extends RuntimeException {

    public CantChangeNameOnUpdateException(String message){ super(message);}
}

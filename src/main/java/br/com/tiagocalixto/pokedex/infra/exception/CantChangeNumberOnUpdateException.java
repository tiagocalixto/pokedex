package br.com.tiagocalixto.pokedex.infra.exception;

public class CantChangeNumberOnUpdateException extends RuntimeException {

    public CantChangeNumberOnUpdateException(String message){ super(message);}
}

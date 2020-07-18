package br.com.tiagocalixto.pokedex.infra.exception;

public class NationalDexOutOfServiceException extends RuntimeException {

    public NationalDexOutOfServiceException(String message){ super(message);}
}

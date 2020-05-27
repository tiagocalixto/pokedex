package br.com.tiagocalixto.pokedex.ports.data_source_ports;

public interface ExistsByIdRepositoryPort {

    boolean isExistsById(Long id);
}

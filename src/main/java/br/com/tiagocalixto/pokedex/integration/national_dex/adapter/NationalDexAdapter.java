package br.com.tiagocalixto.pokedex.integration.national_dex.adapter;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.facade.GetPokemonFromApiFacade;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.FindOneByIdRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.data_source_ports.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("NationalDex")
public class NationalDexAdapter implements FindOneByIdIntegrationPort<Pokemon> {

    //<editor-fold: properties>
    private GetPokemonFromApiFacade getFromNationalDex;
    private ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter;
    private InsertRepositoryPort<PokemonNationalDexDto> saveCache;
    private FindOneByIdRepositoryPort<PokemonNationalDexDto> findByIdCache;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public NationalDexAdapter(GetPokemonFromApiFacade getFromNationalDex,
                              ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter,
                              @Qualifier("NationalDexCache") InsertRepositoryPort<PokemonNationalDexDto> saveCache,
                              @Qualifier("NationalDexCache") FindOneByIdRepositoryPort<PokemonNationalDexDto> findByIdCache) {

        this.getFromNationalDex = getFromNationalDex;
        this.converter = converter;
        this.saveCache = saveCache;
        this.findByIdCache = findByIdCache;
    }
    //</editor-fold>


    @Override
    public Optional<Pokemon> findById(Long id) {

        Optional<PokemonNationalDexDto> entity = findByIdCache.findById(id);

        if (entity.isEmpty()) {
            entity = getFromNationalDex.getPokemon(id);
            entity.ifPresent(item -> saveCache.insertAsync(item));
        }

        return converter.convertToDomain(entity);
    }
}

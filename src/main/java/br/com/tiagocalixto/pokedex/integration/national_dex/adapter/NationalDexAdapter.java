package br.com.tiagocalixto.pokedex.integration.national_dex.adapter;

import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.integration.national_dex.converter.ConverterNationalDex;
import br.com.tiagocalixto.pokedex.integration.national_dex.dto.pokemon.PokemonNationalDexDto;
import br.com.tiagocalixto.pokedex.integration.national_dex.facade.GetPokemonFromApiFacade;
import br.com.tiagocalixto.pokedex.ports.integration.FindOneByIdIntegrationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("NationalDex")
public class NationalDexAdapter implements FindOneByIdIntegrationPort<Pokemon> {

    //<editor-fold: properties>
    private GetPokemonFromApiFacade getFromNationalDex;
    private ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter;
    //</editor-fold>

    //<editor-fold: constructor>
    @Autowired
    public NationalDexAdapter(GetPokemonFromApiFacade getFromNationalDex,
                              ConverterNationalDex<PokemonNationalDexDto, Pokemon> converter) {

        this.getFromNationalDex = getFromNationalDex;
        this.converter = converter;
    }
    //</editor-fold>


    @Override
    @Cacheable(value = "NationalDex", key = "{#id}")
    public Optional<Pokemon> findById(Long id) {

        return converter.convertToDomain(getFromNationalDex.getPokemon(id));
    }
}

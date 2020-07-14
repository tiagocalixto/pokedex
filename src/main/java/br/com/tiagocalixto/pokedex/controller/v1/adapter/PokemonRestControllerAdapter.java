package br.com.tiagocalixto.pokedex.controller.v1.adapter;

import br.com.tiagocalixto.pokedex.controller.v1.converter.ConverterDto;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.ports.entry_point.delete.DeleteEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetAllByNameEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetOneByIdEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetOneByNumberEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetPageableEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.post.PostEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.put.PutEntryPointPort;
import br.com.tiagocalixto.pokedex.use_case.handler.HandlerUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokemonRestControllerAdapter implements GetOneByIdEntryPointPort<PokemonDto>,
        GetOneByNumberEntryPointPort<PokemonDto>, GetAllByNameEntryPointPort<PokemonDto>,
        GetPageableEntryPointPort<PokemonDto>, PostEntryPointPort<PokemonDto>,
        PutEntryPointPort<PokemonDto>, DeleteEntryPointPort {

    private HandlerUseCase handler;
    private ConverterDto<PokemonDto, Pokemon> converter;

    public PokemonRestControllerAdapter(HandlerUseCase handler, ConverterDto<PokemonDto, Pokemon> converter) {

        this.handler = handler;
        this.converter = converter;
    }


    @Override
    public void deleteById(Long id) {

        handler.deletePokemonById(id);
    }

    @Override
    public List<PokemonDto> getAllByName(String name) {

       return converter.convertToDtoList(handler.findPokemonByName(name));
    }

    @Override
    public PokemonDto getOneById(Long id) {

        return converter.convertToDtoNotOptional(handler.findPokemonById(id));
    }

    @Override
    public PokemonDto getOneByNumber(Long number) {

        return converter.convertToDtoNotOptional(handler.findPokemonByNumber(number));
    }

    @Override
    public List<PokemonDto> getPage(int pageNumber) {

        return converter.convertToDtoList(handler.findPokemonPageable(pageNumber));
    }

    @Override
    public PokemonDto save(PokemonDto pokemon) {

        Pokemon domain = converter.convertToDomainNotOptional(pokemon);
        domain.setId(null);
        return converter.convertToDtoNotOptional(handler.savePokemon(domain));
    }

    @Override
    public PokemonDto update(PokemonDto pokemon) {

        Pokemon domain = converter.convertToDomainNotOptional(pokemon);
        return converter.convertToDtoNotOptional(handler.updatePokemon(domain));
    }
}

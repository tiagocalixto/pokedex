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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PokemonControllerAdapter implements GetOneByIdEntryPointPort<PokemonDto>,
        GetOneByNumberEntryPointPort<PokemonDto>, GetAllByNameEntryPointPort<PokemonDto>,
        GetPageableEntryPointPort<PokemonDto>, PostEntryPointPort<PokemonDto>,
        PutEntryPointPort<PokemonDto>, DeleteEntryPointPort {

    //<editor-fold: properties>
    private HandlerUseCase handler;
    private ConverterDto<PokemonDto, Pokemon> converter;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonControllerAdapter(HandlerUseCase handler, ConverterDto<PokemonDto, Pokemon> converter) {

        this.handler = handler;
        this.converter = converter;
    }
    //</editor-fold>


    @Override
    public PokemonDto getOneById(Long id) {

        log.info("controller service adapter, find by id: {}", id);
        return converter.convertToDtoNotOptional(handler.findPokemonById(id));
    }

    @Override
    public PokemonDto getOneByNumber(Long number) {

        log.info("controller service adapter, find by number: {}", number);
        return converter.convertToDtoNotOptional(handler.findPokemonByNumber(number));
    }

    @Override
    public List<PokemonDto> getAllByName(String name) {

        log.info("controller service adapter, find by name: {}", name);
        return converter.convertToDtoList(handler.findPokemonByName(name));
    }

    @Override
    public List<PokemonDto> getPage(int pageNumber) {

        log.info("controller service adapter, find pageable - page: {}", pageNumber);
        return converter.convertToDtoList(handler.findPokemonPageable(pageNumber));
    }

    @Override
    public PokemonDto save(PokemonDto pokemon) {

        log.info("controller service adapter, save pokemon: {}", pokemon);

        Pokemon domain = converter.convertToDomainNotOptional(pokemon);
        domain.setId(null);

        return converter.convertToDtoNotOptional(handler.savePokemon(domain));
    }

    @Override
    public PokemonDto update(PokemonDto pokemon) {

        log.info("controller service adapter, update pokemon: {}", pokemon);
        Pokemon domain = converter.convertToDomainNotOptional(pokemon);
        return converter.convertToDtoNotOptional(handler.updatePokemon(domain));
    }

    @Override
    public void deleteById(Long id) {

        log.info("controller service adapter, delete by id: {}", id);
        handler.deletePokemonById(id);
    }
}

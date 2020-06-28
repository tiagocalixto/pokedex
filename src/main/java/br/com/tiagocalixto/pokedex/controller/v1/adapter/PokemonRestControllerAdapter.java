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
import br.com.tiagocalixto.pokedex.use_case.mediator.PokemonMediatorUseCase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.POKEMON_NOT_FOUND_BY_ID;

@Component
public class PokemonRestControllerAdapter implements GetOneByIdEntryPointPort<PokemonDto>,
        GetOneByNumberEntryPointPort<PokemonDto>, GetAllByNameEntryPointPort<PokemonDto>,
        GetPageableEntryPointPort<PokemonDto>, PostEntryPointPort<PokemonDto>,
        PutEntryPointPort<PokemonDto>, DeleteEntryPointPort {

    private PokemonMediatorUseCase pokemonMediator;
    private ConverterDto<PokemonDto, Pokemon> converter;

    public PokemonRestControllerAdapter(PokemonMediatorUseCase pokemonMediator,
                                        ConverterDto<PokemonDto, Pokemon> converter) {
        this.pokemonMediator = pokemonMediator;
        this.converter = converter;
    }

    @Override
    public void deleteById(Long id) {

        pokemonMediator.pokemonDeleteById(id);
    }

    @Override
    public List<PokemonDto> getAllByName(String name) {

       return converter.convertToDtoList(pokemonMediator.pokemonFindAllByName(name));
    }

    @Override
    public PokemonDto getOneById(Long id) {

        return converter.convertToDtoNotOptional(pokemonMediator.pokemonFindById(id));
    }

    @Override
    public PokemonDto getOneByNumber(Long number) {

        return converter.convertToDtoNotOptional(pokemonMediator.pokemonFindByNumber(number));
    }

    @Override
    public List<PokemonDto> getPage(int pageNumber) {

        return converter.convertToDtoList(pokemonMediator.pokemonFindPageable(pageNumber));
    }

    @Override
    public PokemonDto save(PokemonDto pokemon) {

        Pokemon domain = converter.convertToDomainNotOptional(pokemon);
        domain.setId(null);
        return converter.convertToDtoNotOptional(pokemonMediator.save(domain));
    }

    @Override
    public PokemonDto update(PokemonDto pokemon) {

        Pokemon domain = converter.convertToDomainNotOptional(pokemon);
        return converter.convertToDtoNotOptional(pokemonMediator.update(domain));
    }
}

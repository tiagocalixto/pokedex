package br.com.tiagocalixto.pokedex.controller.rest;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.ports.entry_point.delete.DeleteEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetAllByNameEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetOneByIdEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetOneByNumberEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetPageableEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.post.PostEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.put.PutEntryPointPort;
import com.google.gson.Gson;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.reset;

public class PokemonControllerMockBean {

    protected Gson json = new Gson();

    @MockBean
    protected GetOneByIdEntryPointPort<PokemonDto> getOneByIdPort;

    @MockBean
    protected GetOneByNumberEntryPointPort<PokemonDto> getOneByNUmberPort;

    @MockBean
    protected GetAllByNameEntryPointPort<PokemonDto> getAllByNamePort;

    @MockBean
    protected GetPageableEntryPointPort<PokemonDto> getPageablePort;

    @MockBean
    protected PostEntryPointPort<PokemonDto> postPort;

    @MockBean
    protected PutEntryPointPort<PokemonDto> putPort;

    @MockBean
    protected DeleteEntryPointPort deletePort;


    protected void resetMock(){

        reset(getOneByIdPort);
        reset(getOneByNUmberPort);
        reset(getAllByNamePort);
        reset(getPageablePort);
        reset(postPort);
        reset(putPort);
        reset(deletePort);
    }
}

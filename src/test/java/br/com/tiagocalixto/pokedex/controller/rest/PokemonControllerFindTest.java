package br.com.tiagocalixto.pokedex.controller.rest;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.rest.PokemonRestController;
import br.com.tiagocalixto.pokedex.infra.exception.NoContentException;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PokemonRestController.class)
public class PokemonControllerFindTest extends PokemonControllerMockBean {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenId_whenGetOneById_logged_theReturnPokemon() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getOneByIdPort.getOneById(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/id/" + pokemon.getId().toString())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(getOneByIdPort, times(1)).getOneById(anyLong());
        resetMock();
    }

    @Test
    @SneakyThrows
    public void givenId_whenGetOneById_notLogged_thenReturnUnauthorized() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getOneByIdPort.getOneById(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/id/" + pokemon.getId().toString())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(getOneByIdPort, times(0)).getOneById(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenId_whenGetOneByIdNotFound_logged_thenReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getOneByIdPort.getOneById(anyLong())).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/v1/pokemon/id/" + pokemon.getId().toString())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getOneByIdPort, times(1)).getOneById(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenInvalidId_whenGetOneByIdNotFound_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setId(0L);

        when(getOneByIdPort.getOneById(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/id/" + pokemon.getId())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getOneByIdPort, times(0)).getOneById(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNumber_whenGetOneByNumber_logged_thenReturnPokemon() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getOneByNUmberPort.getOneByNumber(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/number/" + pokemon.getNumber())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(getOneByNUmberPort, times(1)).getOneByNumber(anyLong());
        resetMock();
    }

    @Test
    @SneakyThrows
    public void givenNumber_whenGetOneByNumber_notLogged_thenReturnUnauthorized() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getOneByNUmberPort.getOneByNumber(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/number/" + pokemon.getNumber())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(getOneByNUmberPort, times(0)).getOneByNumber(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNumber_whenGetOneByNumberNotFound_logged_thenReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getOneByNUmberPort.getOneByNumber(anyLong())).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/v1/pokemon/number/" + pokemon.getNumber())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getOneByNUmberPort, times(1)).getOneByNumber(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNumberLessThanOne_whenGetOneByNumber_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(0L);

        when(getOneByNUmberPort.getOneByNumber(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/number/" + pokemon.getNumber())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getOneByNUmberPort, times(0)).getOneByNumber(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNumberGreaterThanOneHundredFifthOne_whenGetOneByNumber_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(557L);

        when(getOneByNUmberPort.getOneByNumber(anyLong())).thenReturn(pokemon);

        mvc.perform(get("/v1/pokemon/number/" + pokemon.getNumber())
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getOneByNUmberPort, times(0)).getOneByNumber(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenName_whenGetAllByName_logged_thenReturnPokemonList() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getAllByNamePort.getAllByName(anyString())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/name/" + pokemon.getName())
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(getAllByNamePort, times(1)).getAllByName(anyString());
        resetMock();
    }

    @Test
    @SneakyThrows
    public void givenName_whenGetAllByName_notLogged_thenReturnUnauthorized() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getAllByNamePort.getAllByName(anyString())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/name/" + pokemon.getName())
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(getAllByNamePort, times(0)).getAllByName(anyString());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenName_whenGetAllByNameNotFound_logged_thenReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(getAllByNamePort.getAllByName(anyString())).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/v1/pokemon/name/" + pokemon.getName())
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getAllByNamePort, times(1)).getAllByName(anyString());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNameTooShort_whenGetAllByName_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName("Ka");

        when(getAllByNamePort.getAllByName(anyString())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/name/" + pokemon.getName())
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getAllByNamePort, times(0)).getAllByName(anyString());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNameTooLong_whenGetAllByName_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName("K".repeat(55));

        when(getAllByNamePort.getAllByName(anyString())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/name/" + pokemon.getName())
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getAllByNamePort, times(0)).getAllByName(anyString());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenNameWithSpecialChar_whenGetAllByName_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName(pokemon.getName().concat("$"));

        when(getAllByNamePort.getAllByName(anyString())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/name/" + pokemon.getName())
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getAllByNamePort, times(0)).getAllByName(anyString());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPageNumber_whenGetPageable_logged_thenReturnPokemonList() {

        PokemonDto pokemon = MocksDto.createPokemon();
        int pageNumber = 1;

        when(getPageablePort.getPage(anyInt())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/pageable/" + pageNumber)
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(getPageablePort, times(1)).getPage(anyInt());
        resetMock();
    }

    @Test
    @SneakyThrows
    public void givenPageNumber_whenGetPageable_notLogged_thenReturnUnauthorized() {

        PokemonDto pokemon = MocksDto.createPokemon();
        int pageNumber = 1;

        when(getPageablePort.getPage(anyInt())).thenReturn(List.of(pokemon));

        mvc.perform(get("/v1/pokemon/pageable/" + pageNumber)
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(getPageablePort, times(0)).getPage(anyInt());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPageNumber_whenGetPageableNoContent_logged_thenReturnNoContent() {

        PokemonDto pokemon = MocksDto.createPokemon();
        int pageNumber = 1;

        when(getPageablePort.getPage(anyInt())).thenThrow(NoContentException.class);

        mvc.perform(get("/v1/pokemon/pageable/" + pageNumber)
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(getPageablePort, times(1)).getPage(anyInt());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPageNumberInvalid_whenGetPageable_logged_thenReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        int pageNumber = 0;

        when(getPageablePort.getPage(anyInt())).thenThrow(NoContentException.class);

        mvc.perform(get("/v1/pokemon/pageable/" + pageNumber)
                .content(json.toJson(List.of(pokemon)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(getPageablePort, times(0)).getPage(anyInt());
        resetMock();
    }

}

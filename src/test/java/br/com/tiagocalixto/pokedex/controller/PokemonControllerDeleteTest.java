package br.com.tiagocalixto.pokedex.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PokemonRestController.class)
public class PokemonControllerDeleteTest extends PokemonControllerMockBean {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenId_whenDeleteById_logged_theReturnReturnOk() {

        PokemonDto pokemon = MocksDto.createPokemon();

        doNothing().when(deletePort).deleteById(anyLong());

        mvc.perform(delete("/v1/pokemon/" + pokemon.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(deletePort, times(1)).deleteById(anyLong());
        resetMock();
    }

    @Test
    @SneakyThrows
    public void givenId_whenDeleteById_notLogged_theReturnReturnUnauthorized() {

        PokemonDto pokemon = MocksDto.createPokemon();

        doNothing().when(deletePort).deleteById(anyLong());

        mvc.perform(delete("/v1/pokemon/" + pokemon.getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(deletePort, times(0)).deleteById(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenId_whenDeleteByIdNotFound_logged_theReturnReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        doThrow(EntityNotFoundException.class).when(deletePort).deleteById(anyLong());

        mvc.perform(delete("/v1/pokemon/" + pokemon.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(deletePort, times(1)).deleteById(anyLong());
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenIdTooLess_whenDeleteById_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setId(0L);

        doThrow(EntityNotFoundException.class).when(deletePort).deleteById(anyLong());

        mvc.perform(delete("/v1/pokemon/" + pokemon.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(deletePort, times(0)).deleteById(anyLong());
        resetMock();
    }

}

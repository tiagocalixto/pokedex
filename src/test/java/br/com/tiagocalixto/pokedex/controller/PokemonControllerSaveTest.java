package br.com.tiagocalixto.pokedex.controller;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.rest.PokemonRestController;
import br.com.tiagocalixto.pokedex.infra.exception.*;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PokemonRestController.class)
public class PokemonControllerSaveTest extends PokemonControllerMockBean {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemon_whenSave_logged_theReturnReturnPokemonCreated() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @SneakyThrows
    public void givenPokemon_whenSave_notLogged_theReturnReturnPokemonCreated() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonAlreadyExistent_whenSave_logged_theReturnReturnUnprocessableEntity() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenThrow(PokemonAlreadyExistsException.class);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemon_whenSaveNationalDexOutOfService_logged_theReturnReturnServiceUnavailable() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenThrow(NationalDexOutOfServiceException.class);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithInvalidName_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenThrow(PokemonNameIncorrectException.class);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithInvalidType_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenThrow(PokemonIncorrectTypeException.class);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithInvalidMove_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenThrow(PokemonMoveIncorrectException.class);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithInvalidEvolution_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(postPort.save(any(PokemonDto.class))).thenThrow(PokemonEvolutionIncorrectException.class);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(1)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNumberTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNumberTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(399L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNoNumber_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNameNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNameSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName(pokemon.getName().concat("&"));

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNameTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName("Ka");

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithNameTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName("K".repeat(55));

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithWeightTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeight(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithWeightTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeight(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithWeightNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeight(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithHeightTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setHeight(BigDecimal.valueOf(0));

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithHeightTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setHeight(BigDecimal.valueOf(26.0));

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithHeightNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setHeight(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithAboutSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbout(pokemon.getAbout().concat("#"));

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithAboutTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbout("oi");

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithAboutTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbout("o".repeat(256));

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithUrlPictureNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setUrlPicture(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithUrlPictureBlank_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setUrlPicture("");

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithUrlPictureInvalid_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setUrlPicture("this is my website");

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setStats(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsHpNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setHp(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsHpToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setHp(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsHpToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setHp(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsAttackNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setAttack(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsAttackToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setAttack(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsAttackToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setAttack(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsDefenseNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setDefense(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsDefenseToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setDefense(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsDefenseToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setDefense(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpecialAttackNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialAttack(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpecialAttackToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialAttack(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpecialAttackToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialAttack(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpecialDefenseNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialDefense(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpecialDefenseToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialDefense(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpecialDefenseToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialDefense(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpeedNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpeed(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpeedToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpeed(0L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithStatsSpeedToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpeed(1001L);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithTypeNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setType(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithTypeEmpty_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setType(Collections.emptyList());

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithTypeDuplicatedItem_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setType(new ArrayList<>());
        pokemon.getType().add(MocksDto.createType());
        pokemon.getType().add(MocksDto.createType());

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithTypeDescriptionNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getType().get(0).setDescription(null);

        when(postPort.save(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(post("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(postPort, times(0)).save(any(PokemonDto.class));
        resetMock();
    }
}

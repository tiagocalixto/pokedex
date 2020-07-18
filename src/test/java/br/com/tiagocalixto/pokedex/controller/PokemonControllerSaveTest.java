package br.com.tiagocalixto.pokedex.controller;

import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionTriggerDtoEnum;
import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.rest.PokemonRestController;
import br.com.tiagocalixto.pokedex.infra.exception.*;
import br.com.tiagocalixto.pokedex.mock.MocksDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    public void givenPokemon_whenSave_notLogged_theReturnReturnUnauthorized() {

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

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    public void givenPokemonWithEvolvedFromEvolutionTriggerNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setTrigger(null);

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
    public void givenPokemonWithEvolvedFromEvolutionTriggerLevelUpAndLevelNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.LEVEL_UP);
        pokemon.getEvolvedFrom().setLevel(null);

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
    public void givenPokemonWithEvolvedFromEvolutionTriggerUseItemAndItemNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.USE_ITEM);
        pokemon.getEvolvedFrom().setItem(null);

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
    public void givenPokemonWithEvolvedFromPokemonNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setPokemon(null);

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
    public void givenPokemonWithEvolvedFromPokemonNumberTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setNumber(0L);

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
    public void givenPokemonWithEvolvedFromPokemonNumberTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setNumber(155L);

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
    public void givenPokemonWithEvolvedFromPokemonNameSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(pokemon.getEvolvedFrom().getPokemon().getName() + "$");

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
    public void givenPokemonWithEvolvedFromPokemonNameTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName("AA");

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
    public void givenPokemonWithEvolvedFromPokemonNameTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName("A".repeat(58));

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
    public void givenPokemonWithEvolvedFromPokemonNumberInvalidAndNameInvalid_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName("");
        pokemon.getEvolvedFrom().getPokemon().setNumber(0L);

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
    public void givenPokemonWithEvolvedFromPokemonNumberNullAndNameNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(null);
        pokemon.getEvolvedFrom().getPokemon().setNumber(null);

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
    public void givenPokemonWithEvolvedFromHimself_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(pokemon.getName());

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
    public void givenPokemonWithEvolveToEvolutionTriggerNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setTrigger(null);

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
    public void givenPokemonWithEvolveToEvolutionTriggerLevelUpAndLevelNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setTrigger(EvolutionTriggerDtoEnum.LEVEL_UP);
        pokemon.getEvolveTo().get(0).setLevel(null);

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
    public void givenPokemonWithEvolveToEvolutionTriggerUseItemAndItemNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setTrigger(EvolutionTriggerDtoEnum.USE_ITEM);
        pokemon.getEvolveTo().get(0).setItem(null);

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
    public void givenPokemonWithEvolveToPokemonNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setPokemon(null);

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
    public void givenPokemonWithEvolveToPokemonNumberTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(0L);

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
    public void givenPokemonWithEvolveToPokemonNumberTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(155L);

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
    public void givenPokemonWithEvolveToPokemonNameSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName(pokemon.getEvolvedFrom().getPokemon().getName() + "$");

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
    public void givenPokemonWithEvolveToPokemonNameTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName("AA");

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
    public void givenPokemonWithEvolveToPokemonNameTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName("A".repeat(58));

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
    public void givenPokemonWithEvolveToPokemonNumberInvalidAndNameInvalid_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName("");
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(0L);

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
    public void givenPokemonWithEvolveToPokemonNumberNullAndNameNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName(null);
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(null);

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
    public void givenPokemonWithEvolveToDuplicated_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setEvolveTo(new ArrayList<>());
        pokemon.getEvolveTo().add(MocksDto.createPokemonEvolutionTrade(MocksDto.createEvolveTo()));
        pokemon.getEvolveTo().add(MocksDto.createPokemonEvolutionTrade(MocksDto.createEvolveTo()));

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
    public void givenPokemonWithEvolveToHimself_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(pokemon.getNumber());

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
    public void givenPokemonWithMoveDuplicated_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setMove(new ArrayList<>());
        pokemon.getMove().add(MocksDto.createPokemonMove(MocksDto.createMove()));
        pokemon.getMove().add(MocksDto.createPokemonMove(MocksDto.createMove()));

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
    public void givenPokemonWithPokemonMoveMoveNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setMove(null);

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
    public void givenPokemonWithPokemonMoveLevelLearnNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setLevelLearn(null);

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
    public void givenPokemonWithPokemonMoveLevelLearnTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setLevelLearn(0L);

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
    public void givenPokemonWithPokemonMoveLevelLearnTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setLevelLearn(101L);

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
    public void givenPokemonWithPokemonMoveMoveDescriptionNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription(null);

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
    public void givenPokemonWithPokemonMoveMoveDescriptionEmpty_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription("");

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
    public void givenPokemonWithPokemonMoveMoveDescriptionSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription(pokemon.getMove().get(0).getMove().getDescription() + "*");

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
    public void givenPokemonWithPokemonMoveMoveDescriptionToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription("b");

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
    public void givenPokemonWithPokemonMoveMoveDescriptionToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription("b".repeat(99));

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
    public void givenPokemonWithPokemonMoveMoveTypeNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setType(null);

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
    public void givenPokemonWithPokemonMoveMoveTypeDescriptionNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().getType().setDescription(null);

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
    public void givenPokemonWithPokemonMoveMovePpNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPp(null);

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
    public void givenPokemonWithPokemonMoveMovePpTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPp(0L);

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
    public void givenPokemonWithPokemonMoveMovePpTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPp(1001L);

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
    public void givenPokemonWithPokemonMoveMovePowerNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPower(null);

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
    public void givenPokemonWithPokemonMoveMovePowerTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPower(0L);

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
    public void givenPokemonWithPokemonMoveMovePowerTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPower(1001L);

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
    public void givenPokemonWithPokemonMoveMoveAccuracyNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAccuracy(null);

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
    public void givenPokemonWithPokemonMoveMoveAccuracyTooSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAccuracy(BigDecimal.valueOf(0));

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
    public void givenPokemonWithPokemonMoveMoveAccuracyTooBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAccuracy(BigDecimal.valueOf(101));

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
    public void givenPokemonWithPokemonMoveMoveAboutSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAbout("Any Thing with $p&ci@l char");

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
    public void givenPokemonWithPokemonMoveMoveAboutToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAbout("A");

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
    public void givenPokemonWithPokemonMoveMoveAboutToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAbout("A".repeat(300));

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
    public void givenPokemonWithPokemonAbilityDuplicated_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbility(new ArrayList<>());
        pokemon.getAbility().add(MocksDto.createAbility());
        pokemon.getAbility().add(MocksDto.createAbility());

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
    public void givenPokemonWithPokemonAbilityDescriptionNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription(null);

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
    public void givenPokemonWithPokemonAbilityDescriptionSpecialChar_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription("D#$cr!pt!0n");

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
    public void givenPokemonWithPokemonAbilityDescriptionToSmall_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription("D");

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
    public void givenPokemonWithPokemonAbilityDescriptionToBig_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription("D".repeat(67));

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
    public void givenPokemonWithPokemonWeaknessesDuplicated_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeakness(new ArrayList<>());
        pokemon.getWeakness().add(MocksDto.createWeakness());
        pokemon.getWeakness().add(MocksDto.createWeakness());

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
    public void givenPokemonWithPokemonWeaknessesTypeDescriptionNull_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getWeakness().get(0).setDescription(null);

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
    public void givenPokemonWithPokemonWeaknessesEqualType_whenSave_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeakness(pokemon.getType());

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

package br.com.tiagocalixto.pokedex.controller.rest;

import br.com.tiagocalixto.pokedex.controller.v1.dto.enums.EvolutionTriggerDtoEnum;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PokemonRestController.class)
class PokemonControllerUpdateTest extends PokemonControllerMockBean {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemon_whenUpdate_logged_theReturnReturnOk() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @SneakyThrows
    void givenPokemon_whenUpdate_notLogged_theReturnReturnUnauthorized() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonNotExistent_whenUpdate_logged_theReturnReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(EntityNotFoundException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonTryingToChangeName_whenUpdate_logged_theReturnReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(CantChangeNameOnUpdateException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonTryingToChangeNumber_whenUpdate_logged_theReturnReturnNotFound() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(CantChangeNumberOnUpdateException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemon_whenUpdateNationalDexOutOfService_logged_theReturnReturnServiceUnavailable() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(NationalDexOutOfServiceException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithInvalidType_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(PokemonIncorrectTypeException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithInvalidMove_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(PokemonMoveIncorrectException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithInvalidEvolution_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();

        when(putPort.update(any(PokemonDto.class))).thenThrow(PokemonEvolutionIncorrectException.class);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(1)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNumberTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNumberTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(399L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNoNumber_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setNumber(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNameNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNameSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName(pokemon.getName().concat("&"));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNameTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName("Ka");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithNameTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setName("K".repeat(55));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithWeightTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeight(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithWeightTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeight(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithWeightNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeight(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithHeightTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setHeight(BigDecimal.valueOf(0));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithHeightTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setHeight(BigDecimal.valueOf(26.0));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithHeightNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setHeight(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithAboutSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbout(pokemon.getAbout().concat("#"));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithAboutTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbout("oi");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithAboutTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbout("o".repeat(256));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithUrlPictureNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setUrlPicture(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithUrlPictureBlank_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setUrlPicture("");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithUrlPictureInvalid_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setUrlPicture("this is my website");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setStats(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsHpNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setHp(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsHpToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setHp(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsHpToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setHp(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsAttackNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setAttack(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsAttackToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setAttack(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsAttackToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setAttack(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsDefenseNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setDefense(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsDefenseToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setDefense(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsDefenseToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setDefense(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpecialAttackNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialAttack(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpecialAttackToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialAttack(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpecialAttackToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialAttack(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpecialDefenseNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialDefense(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpecialDefenseToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialDefense(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpecialDefenseToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpecialDefense(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpeedNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpeed(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpeedToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpeed(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithStatsSpeedToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getStats().setSpeed(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithTypeNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setType(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithTypeEmpty_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setType(Collections.emptyList());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithTypeDuplicatedItem_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setType(new ArrayList<>());
        pokemon.getType().add(MocksDto.createType());
        pokemon.getType().add(MocksDto.createType());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithTypeDescriptionNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getType().get(0).setDescription(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromEvolutionTriggerNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setTrigger(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromEvolutionTriggerLevelUpAndLevelNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.LEVEL_UP);
        pokemon.getEvolvedFrom().setLevel(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromEvolutionTriggerUseItemAndItemNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setTrigger(EvolutionTriggerDtoEnum.USE_ITEM);
        pokemon.getEvolvedFrom().setItem(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().setPokemon(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNumberTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setNumber(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNumberTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setNumber(155L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNameSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(pokemon.getEvolvedFrom().getPokemon().getName() + "$");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNameTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName("AA");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNameTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName("A".repeat(58));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNumberInvalidAndNameInvalid_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName("");
        pokemon.getEvolvedFrom().getPokemon().setNumber(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromPokemonNumberNullAndNameNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(null);
        pokemon.getEvolvedFrom().getPokemon().setNumber(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolvedFromHimself_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolvedFrom().getPokemon().setName(pokemon.getName());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToEvolutionTriggerNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setTrigger(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToEvolutionTriggerLevelUpAndLevelNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setTrigger(EvolutionTriggerDtoEnum.LEVEL_UP);
        pokemon.getEvolveTo().get(0).setLevel(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToEvolutionTriggerUseItemAndItemNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setTrigger(EvolutionTriggerDtoEnum.USE_ITEM);
        pokemon.getEvolveTo().get(0).setItem(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).setPokemon(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNumberTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNumberTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(155L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNameSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName(pokemon.getEvolvedFrom().getPokemon().getName() + "$");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNameTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName("AA");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNameTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName("A".repeat(58));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNumberInvalidAndNameInvalid_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName("");
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToPokemonNumberNullAndNameNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setName(null);
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToDuplicated_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setEvolveTo(new ArrayList<>());
        pokemon.getEvolveTo().add(MocksDto.createPokemonEvolutionTrade(MocksDto.createEvolveTo()));
        pokemon.getEvolveTo().add(MocksDto.createPokemonEvolutionTrade(MocksDto.createEvolveTo()));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithEvolveToHimself_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getEvolveTo().get(0).getPokemon().setNumber(pokemon.getNumber());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithMoveDuplicated_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setMove(new ArrayList<>());
        pokemon.getMove().add(MocksDto.createPokemonMove(MocksDto.createMove()));
        pokemon.getMove().add(MocksDto.createPokemonMove(MocksDto.createMove()));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setMove(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveLevelLearnNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setLevelLearn(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveLevelLearnTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setLevelLearn(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveLevelLearnTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).setLevelLearn(101L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveDescriptionNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveDescriptionEmpty_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription("");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveDescriptionSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription(pokemon.getMove().get(0).getMove().getDescription() + "*");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveDescriptionToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription("b");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveDescriptionToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setDescription("b".repeat(99));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveTypeNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setType(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveTypeDescriptionNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().getType().setDescription(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMovePpNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPp(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMovePpTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPp(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMovePpTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPp(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMovePowerNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPower(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMovePowerTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPower(0L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMovePowerTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setPower(1001L);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveAccuracyNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAccuracy(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveAccuracyTooSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAccuracy(BigDecimal.valueOf(0));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveAccuracyTooBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAccuracy(BigDecimal.valueOf(101));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveAboutSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAbout("Any Thing with $p&ci@l char");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveAboutToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAbout("A");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonMoveMoveAboutToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getMove().get(0).getMove().setAbout("A".repeat(300));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonAbilityDuplicated_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setAbility(new ArrayList<>());
        pokemon.getAbility().add(MocksDto.createAbility());
        pokemon.getAbility().add(MocksDto.createAbility());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonAbilityDescriptionNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonAbilityDescriptionSpecialChar_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription("D#$cr!pt!0n");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonAbilityDescriptionToSmall_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription("D");

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonAbilityDescriptionToBig_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getAbility().get(0).setDescription("D".repeat(67));

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonWeaknessesDuplicated_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeakness(new ArrayList<>());
        pokemon.getWeakness().add(MocksDto.createWeakness());
        pokemon.getWeakness().add(MocksDto.createWeakness());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonWeaknessesTypeDescriptionNull_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.getWeakness().get(0).setDescription(null);

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }

    @Test
    @WithMockUser("admin")
    @SneakyThrows
    void givenPokemonWithPokemonWeaknessesEqualType_whenUpdate_logged_theReturnReturnBadRequest() {

        PokemonDto pokemon = MocksDto.createPokemon();
        pokemon.setWeakness(pokemon.getType());

        when(putPort.update(any(PokemonDto.class))).thenReturn(pokemon);

        mvc.perform(put("/v1/pokemon")
                .content(json.toJson(pokemon))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(putPort, times(0)).update(any(PokemonDto.class));
        resetMock();
    }
}

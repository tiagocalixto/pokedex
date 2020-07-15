package br.com.tiagocalixto.pokedex.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PokemonControllerTest {


/*

    @Test
    @WithMockUser("admin")
    public void givenPerson_whenFindByIdLogged_thenReturnOk() throws Exception {

        PersonDto person = MockDtos.createPersonDtoPatientWithValidCpf();
        doReturn(person).when(serviceAdapter).findById(anyLong());

        mvc.perform(get("/person/v1/" + person.getId().toString())
                .content(Util.convertObjectToJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(serviceAdapter, times(1)).findById(anyLong());
        reset(serviceAdapter);
    }*/
}

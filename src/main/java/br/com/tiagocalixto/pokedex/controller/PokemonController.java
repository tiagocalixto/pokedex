package br.com.tiagocalixto.pokedex.controller;

import br.com.tiagocalixto.pokedex.controller.adapter.PokemonControllerAdapter;
import br.com.tiagocalixto.pokedex.controller.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.input_rules.groups.ValidationOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.util.Constant.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/pokemon")
@Api(value = "Pokemon")
@Validated
public class PokemonController {

    private PokemonControllerAdapter useCaseAdapter;

    @Autowired
    public PokemonController(PokemonControllerAdapter useCaseAdapter) {

        this.useCaseAdapter = useCaseAdapter;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "Pokemon not found!")})
    @GetMapping(value = "/number/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> searchPokemonByNumber(@Min(value = 1, message = NUMBER_INVALID_RANGE)
                                                            @Max(value = 151, message = NUMBER_INVALID_RANGE)
                                                            @PathVariable Long number) {

        return new ResponseEntity<>(useCaseAdapter.findByNumber(number), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "Pokemon not found!")})
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PokemonDto>> searchPokemonByName(@Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú .']*$", message = NAME_IS_INVALID)
                                                                @Length(min = 3, max = 50,
                                                                        message = NAME_INVALID_SIZE)
                                                                @PathVariable String name) {

        return new ResponseEntity<>(useCaseAdapter.findByName(name), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "Any Pokemon founded!")})
    @GetMapping(value = "/pageable/{pageNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PokemonDto>> searchPokemonPageable(@PathVariable Integer pageNumber) {

        return new ResponseEntity<>(useCaseAdapter.pageableListPokemon(pageNumber), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, response = PokemonDto.class, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> savePokemon(@Validated(ValidationOrder.class) @RequestBody PokemonDto payload) {

        return new ResponseEntity<>(useCaseAdapter.save(payload), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, response = PokemonDto.class, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Pokemon not found!"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> updatePokemon(@Validated(ValidationOrder.class) @RequestBody PokemonDto payload) {

        return new ResponseEntity<>(useCaseAdapter.update(payload), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Pokemon not found")})
    @DeleteMapping(value = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deletePokemon(@Min(value = 1, message = NUMBER_INVALID_RANGE)
                                                    @Max(value = 151, message = NUMBER_INVALID_RANGE)
                                                    @PathVariable Long number) {

        useCaseAdapter.delete(number);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

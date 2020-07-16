package br.com.tiagocalixto.pokedex.controller.v1.rest;

import br.com.tiagocalixto.pokedex.controller.v1.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.controller.v1.input_rules.groups.ValidationOrder;
import br.com.tiagocalixto.pokedex.ports.entry_point.delete.DeleteEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetAllByNameEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetOneByIdEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetOneByNumberEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.get.GetPageableEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.post.PostEntryPointPort;
import br.com.tiagocalixto.pokedex.ports.entry_point.put.PutEntryPointPort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

import static br.com.tiagocalixto.pokedex.infra.constant.Constant.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/pokemon")
@Api(value = "Pokemon")
@Validated
public class PokemonRestController {

    //<editor-fold: properties>
    private GetOneByIdEntryPointPort<PokemonDto> getOneByIdPort;
    private GetOneByNumberEntryPointPort<PokemonDto> getOneByNUmberPort;
    private GetAllByNameEntryPointPort<PokemonDto> getAllByNamePort;
    private GetPageableEntryPointPort<PokemonDto> getPageablePort;
    private PostEntryPointPort<PokemonDto> postPort;
    private PutEntryPointPort<PokemonDto> putPort;
    private DeleteEntryPointPort deletePort;
    //</editor-fold>

    //<editor-fold: constructor>
    public PokemonRestController(GetOneByIdEntryPointPort<PokemonDto> getOneByIdPort,
                                 GetOneByNumberEntryPointPort<PokemonDto> getOneByNUmberPort,
                                 GetAllByNameEntryPointPort<PokemonDto> getAllByNamePort,
                                 GetPageableEntryPointPort<PokemonDto> getPageablePort,
                                 PostEntryPointPort<PokemonDto> postPort, PutEntryPointPort<PokemonDto> putPort,
                                 DeleteEntryPointPort deletePort) {

        this.getOneByIdPort = getOneByIdPort;
        this.getOneByNUmberPort = getOneByNUmberPort;
        this.getAllByNamePort = getAllByNamePort;
        this.getPageablePort = getPageablePort;
        this.postPort = postPort;
        this.putPort = putPort;
        this.deletePort = deletePort;
    }
    //</editor-fold>


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "Pokemon not found!")})
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> getById(@Min(value = 1, message = ID_TOO_SMALL) @PathVariable Long id) {

        return new ResponseEntity<>(getOneByIdPort.getOneById(id), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "Pokemon not found!")})
    @GetMapping(value = "/number/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> getByNumber(@Min(value = 1, message = NUMBER_INVALID_RANGE)
                                                  @Max(value = 151, message = NUMBER_INVALID_RANGE)
                                                  @PathVariable Long number) {

        return new ResponseEntity<>(getOneByNUmberPort.getOneByNumber(number), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "Pokemon not found!")})
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PokemonDto>> getAllByByName(@Pattern(regexp = "^[a-zà-ú-A-ZÀ-Ú .']*$",
            message = NAME_IS_INVALID)
                                                           @Length(min = 3, max = 50,
                                                                   message = NAME_INVALID_SIZE)
                                                           @PathVariable String name) {

        return new ResponseEntity<>(getAllByNamePort.getAllByName(name), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PokemonDto.class),
            @ApiResponse(code = 404, message = "No Pokemon founded!")})
    @GetMapping(value = "/pageable/{pageNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PokemonDto>> getPageable(@Min(value = 1, message = PAGE_NUMBER_TOO_SMALL)
                                                        @PathVariable Integer pageNumber) {

        return new ResponseEntity<>(getPageablePort.getPage(pageNumber), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, response = PokemonDto.class, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> save(@Validated(ValidationOrder.class) @RequestBody PokemonDto payload) {

        return new ResponseEntity<>(postPort.save(payload), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, response = PokemonDto.class, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Pokemon not found!"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> update(@Validated(ValidationOrder.class) @RequestBody PokemonDto payload) {

        return new ResponseEntity<>(putPort.update(payload), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Pokemon not found")})
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@Min(value = 1, message = ID_TOO_SMALL)
                                             @PathVariable Long id) {

        deletePort.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

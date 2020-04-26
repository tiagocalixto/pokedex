package br.com.tiagocalixto.pokedex.controller;


import br.com.tiagocalixto.pokedex.controller.dto.pokemon.PokemonDto;
import br.com.tiagocalixto.pokedex.converter.dto.ConverterDto;
import br.com.tiagocalixto.pokedex.converter.entity.ConverterEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.PokemonRepository;
import br.com.tiagocalixto.pokedex.data_source.sql.repository.TypeRepository;
import br.com.tiagocalixto.pokedex.domain.Type;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/test/v1")
@Api(value = "test")
@Validated
public class controllerTest {


    @Autowired
    PokemonRepository repository;

    @Autowired
    ConverterDto<PokemonDto, Pokemon> converterDto;

    @Autowired
    ConverterEntity<TypeEntity, Type> converterType;

    @Autowired
    ConverterEntity<PokemonEntity, Pokemon> converterEntity;

    @Autowired
    TypeRepository typeRepository;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Pokemon.class),
            @ApiResponse(code = 404, message = "Person not found")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonDto> testController(@RequestBody PokemonDto pokemonDto) {

        Pokemon pOkemon = converterDto.convertToDomain(Optional.of(pokemonDto)).get();



        PokemonEntity entity = converterEntity.convertToEntity(Optional.of( pOkemon)).get();


        TypeEntity typeEntity = typeRepository.save(entity.getMoves().get(0).getMove().getType());

        entity.getMoves().get(0).getMove().setType(typeEntity);

     //   if(pokemonDto.getType().get(0).getId() > 0) {
     //       TypeEntity typeEntity = typeRepository.findById(pokemonDto.getType().get(0).getId()).get();
      //      entity.getType().get(0).setType(typeEntity);

       //     TypeEntity weaknesses = new TypeEntity();

         //   entity.getWeaknesses().get(0).setType(typeEntity);
       // }

        repository.save(entity);

        return new ResponseEntity<>(pokemonDto, HttpStatus.OK);
    }
}

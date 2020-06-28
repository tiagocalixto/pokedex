package br.com.tiagocalixto.pokedex.infra.exception.rest_response_handler.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError implements Serializable {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();
    private int status;
    private HttpStatus error;
    private String message;
    private List<String> errors;
}
package br.com.tiagocalixto.pokedex.infra.exception.rest_response_handler;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseExceptionHandler() {
        super();
    }


    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {

        List<String> argumentErrors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(item -> argumentErrors.add(item.getField() + ": " + item.getDefaultMessage() +
                        (Objects.toString(item.getRejectedValue(),"").length() > 15 ? Strings.EMPTY :
                                " - (Rejected value: " + item.getRejectedValue() + ")")));

        ex.getBindingResult().getGlobalErrors()
                .forEach(item -> argumentErrors.add(item.getObjectName() + ": " + item.getDefaultMessage()));

        ApiError apiError = ApiError.builder()
                .message("Error detected on " + ex.getParameter().getExecutable().getName())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST)
                .errors(argumentErrors)
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException ex) {

        log.error(ex.getMessage());

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> constraintErrors = new ArrayList<>();

        constraintViolations.forEach(item -> {
            String field = item.getPropertyPath().toString().substring(item.getPropertyPath().toString().indexOf('.') + 1);
            String errorMessage = item.getMessageTemplate();
            String rejectedValue = Objects.toString(item.getInvalidValue(),"").length() > 15 ? Strings.EMPTY :
                    " - (Rejected value: " + item.getInvalidValue().toString() + ")";
            constraintErrors.add(field + ": " + errorMessage + rejectedValue);
        });

        ApiError apiError = ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST)
                .errors(constraintErrors)
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //todo - include handler exception geral Exception
}
package com.example.template.shared;

import com.example.template.sampleentity.application.SampleEntityNotFoundException;
import com.example.template.user.application.EmailAlreadyRegisteredException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SampleEntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFound(SampleEntityNotFoundException ex) {
    return ErrorResponse.of(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }

  @ExceptionHandler(EmailAlreadyRegisteredException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleEmailConflict(EmailAlreadyRegisteredException ex) {
    return ErrorResponse.of(HttpStatus.CONFLICT.value(), ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
    return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), message);
  }
}

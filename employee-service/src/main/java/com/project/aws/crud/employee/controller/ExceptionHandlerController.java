package com.project.aws.crud.employee.controller;

import com.project.aws.crud.employee.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

/**
 * Controller responsável por tratar as Exceptions e retornar aos serviços
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    /**
     * Responsável por tratar as Exceções de Negócio
     *
     * @param ex exceção levantada
     * @return {@code ResponseEntity<Object>} dados sobre o erro levantado
     */
    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(final BusinessException ex) {
        log.error("M=handleBusinessException, {}", ex.getMessage());
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(messageSource.getMessage(ex.getCodeError(), ex.getParams(), Locale.getDefault()));
    }

    /**
     * Responsável por tratar as Exceções de validação dos campos
     *
     * @param ex exceção levantada
     * @return {@code ResponseEntity<Object>} dados sobre o erro levantado
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        log.error("M=handleMethodArgumentNotValidException, {}", ex.getMessage());

        final BindingResult bindingResult = ex.getBindingResult();

        var error = "";

        for (FieldError fieldError : bindingResult.getFieldErrors()){
            var messageError = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            if(error.isBlank()) {
                error = messageError;
            } else {
                error += ", " + messageError;
            }
        }

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    /**
     * Responsável por tratar as Exceções genéricas
     *
     * @param ex exceção levantada
     * @return {@code ResponseEntity<Object>} dados sobre o erro levantado
     */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleException(final Exception ex) {
        log.error("M=handleException, {}", ex.getMessage());
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ex.getMessage());
    }
}

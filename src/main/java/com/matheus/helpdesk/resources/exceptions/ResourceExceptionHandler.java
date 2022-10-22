package com.matheus.helpdesk.resources.exceptions;

import com.matheus.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.matheus.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){

        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Object not found.",
                ex.getMessage(),
                request.getRequestURI());

                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação de dados",
                ex.getMessage(),
                request.getRequestURI()
        );  return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException ex, HttpServletRequest request){

       ValidationError errors = new ValidationError(System.currentTimeMillis(),
               HttpStatus.BAD_REQUEST.value(),
               "Validation error",
               "Erro na validação dos campos",
               request.getRequestURI());

       for(FieldError x : ex.getBindingResult().getFieldErrors()){
           errors.addErrors(x.getField(), x.getDefaultMessage());
       }

    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

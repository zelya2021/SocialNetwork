package com.ana.app.auth.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationError(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult();
        var errMessages = errors.getAllErrors();
        return new ResponseEntity<>(
                errMessages
                    .stream()
                    .map(message -> new ErrorResponse(message.getDefaultMessage(), HttpStatus.BAD_REQUEST.value())).toList(),
                HttpStatus.BAD_REQUEST
        );

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ErrorResponse>> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(errorResponse));
    }

}

@Getter
@Setter
class ErrorResponse {
    private String message;
    private int statusCode;

    public ErrorResponse(String message, int status){
        this.statusCode = status;
        this.message = message;
    }
}

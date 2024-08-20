package com.myblog8.exception;


import com.myblog8.Payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Global exception handler for handling exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFound exceptions and returns a custom error response.
     *
     * @param exception  the ResourceNotFound exception
     * @param webRequest the current web request
     * @return a ResponseEntity with a custom ErrorDetails object
     */
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFound exception, WebRequest webRequest) {
        // Create a new ErrorDetails object with the current date, exception message, and request description
        ErrorDetails error = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        // Return a ResponseEntity with the ErrorDetails object and a 500 Internal Server Error status
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails > handleGlobalException(
            Exception ex,
            WebRequest webRequest
    ){
        ErrorDetails error=new ErrorDetails(
                new Date(),
                ex.getMessage(),

                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


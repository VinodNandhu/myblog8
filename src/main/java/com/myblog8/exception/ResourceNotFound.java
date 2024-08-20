package com.myblog8.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)// returns 404
public class ResourceNotFound  extends  RuntimeException{
    // Constructor to initialize the message for the exception.
    public ResourceNotFound(String message){
        super(message);//it displays a message in postman
    }
}

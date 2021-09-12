package com.rishabh.lldcabbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CabAlreadyExistsException extends RuntimeException{
    public CabAlreadyExistsException(String message){
        super(message);
    }
}

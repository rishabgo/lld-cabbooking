package com.rishabh.lldcabbooking.exception.advice;

import com.rishabh.lldcabbooking.exception.CabAlreadyExistsException;
import com.rishabh.lldcabbooking.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(CabAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> exception(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}



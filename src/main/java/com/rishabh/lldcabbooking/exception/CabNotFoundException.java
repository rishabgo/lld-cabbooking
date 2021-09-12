package com.rishabh.lldcabbooking.exception;

public class CabNotFoundException extends RuntimeException{

    public CabNotFoundException(String message){
        super(message);
    }
}

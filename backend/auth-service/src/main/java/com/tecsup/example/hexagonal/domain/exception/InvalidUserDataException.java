package com.tecsup.example.hexagonal.domain.exception;

public class InvalidUserDataException extends RuntimeException{
    public InvalidUserDataException(String msg){
        super("Invalid User Data:"+ msg);
    }
}

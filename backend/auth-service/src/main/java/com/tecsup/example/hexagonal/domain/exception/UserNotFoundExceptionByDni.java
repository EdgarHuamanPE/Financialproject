package com.tecsup.example.hexagonal.domain.exception;

public class UserNotFoundExceptionByDni extends RuntimeException {
    public UserNotFoundExceptionByDni  (String dni){
        super("User not found with dni:"+ dni);
    }
}

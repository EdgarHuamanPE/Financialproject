package com.tecsup.example.hexagonal.domain.exception;

public class UserNotFoundExceptionMenorEdad extends RuntimeException {
    public UserNotFoundExceptionMenorEdad  (Integer edad){
        super("No minor Users found. {}"+ edad);
    }
}

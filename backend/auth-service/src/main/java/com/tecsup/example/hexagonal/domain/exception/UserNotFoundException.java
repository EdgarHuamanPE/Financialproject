package com.tecsup.example.hexagonal.domain.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException (Long userId){
        super("User not found with ID:"+ userId);
    }
    public UserNotFoundException (String apellidoPaterno){
        super("User not found with Apellido Paterno:"+ apellidoPaterno);
    }


}

package com.dominio.customer_service.exception;

public class DuplicateDniException extends RuntimeException{
    public DuplicateDniException (String customer){
        super("Customer duplicate with DNI:"+ customer);
    }
}

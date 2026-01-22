package com.tecsup.financial_origination_service.exception;

public class NotFoundClientId extends RuntimeException{
    public NotFoundClientId(String msg){
        super("Not found Client id fron rest template {}"+msg);
    }
}

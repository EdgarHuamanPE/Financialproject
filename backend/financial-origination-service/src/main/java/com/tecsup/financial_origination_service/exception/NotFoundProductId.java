package com.tecsup.financial_origination_service.exception;

public class NotFoundProductId extends RuntimeException{
    public NotFoundProductId(String msg){
        super("Not found product id fron resttemplae {}"+msg);
    }
}

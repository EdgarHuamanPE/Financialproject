package com.tecsup.product_service.exception;

public class DuplicateCodigoException extends RuntimeException {
    public  DuplicateCodigoException(String product){
        super("Product duplicate with codigo:"+ product);
    }
}

package com.tecsup.financial_origination_service.exception;

public class NotFoundCustomerProductId extends RuntimeException{
    public NotFoundCustomerProductId(Long id){
        super("Not found CustomerProduct with id: " +id);
    }
}

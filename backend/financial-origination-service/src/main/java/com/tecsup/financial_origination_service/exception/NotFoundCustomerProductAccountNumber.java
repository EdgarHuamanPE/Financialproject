package com.tecsup.financial_origination_service.exception;

public class NotFoundCustomerProductAccountNumber extends RuntimeException{
    public NotFoundCustomerProductAccountNumber(String accountNumber){
        super("Not found CustomerProduct by AccountNumber:"+ accountNumber);
    }
}

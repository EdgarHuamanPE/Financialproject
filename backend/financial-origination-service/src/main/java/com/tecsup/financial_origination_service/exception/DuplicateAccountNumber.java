package com.tecsup.financial_origination_service.exception;

public class DuplicateAccountNumber extends  RuntimeException{
    public DuplicateAccountNumber(String accountNumber){
        super("Duplicate AccountNumber" + accountNumber);
    }
}

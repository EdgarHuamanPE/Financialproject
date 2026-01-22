package com.dominio.customer_service.exception;

public class NotFoundCustomer extends RuntimeException{
    public NotFoundCustomer(String numberDocument)
    {
        super("Customer Not Found by numberDocument:"+ numberDocument);
    };

    public NotFoundCustomer(Long id)
    {
        super("Customer Not Found by id:"+ id);
    };
}

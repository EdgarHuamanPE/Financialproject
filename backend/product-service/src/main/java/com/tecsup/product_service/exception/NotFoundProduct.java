package com.tecsup.product_service.exception;

public class NotFoundProduct extends  RuntimeException  {
    public NotFoundProduct(String id){
        super("Not found product with "+ id) ;
    }

    public NotFoundProduct(Long id){
        super("Not found product with "+ id) ;
    }
}

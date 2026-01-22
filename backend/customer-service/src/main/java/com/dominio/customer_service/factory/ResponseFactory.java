package com.dominio.customer_service.factory;

import com.dominio.customer_service.dto.MsgResponse;


public class ResponseFactory {
    public MsgResponse badIdCostumer (String id){
        return new MsgResponse("Bad type id input :" + id);
    }
    public MsgResponse docNumberBlank(String docNumber){
        return new MsgResponse("Not found user with Document Number :" + docNumber);
    }
    public MsgResponse notFoundCustomerByDocNumber(String docNumber){
        return new MsgResponse("Not found user with Document Number :" + docNumber);
    }
    public MsgResponse notFoundCustomerById(String id){
        return new MsgResponse("Not found user with id :" + id);
    }
    public MsgResponse dniExistingResponse(String documentNumber) {
        return new MsgResponse("Duplicate DNI : "+ documentNumber);
    }
    public MsgResponse missingFieldRequerid(){
        return new MsgResponse("Missing Filed Required");
    }
    public MsgResponse createDeleteResponse(String entityName) {
        return new MsgResponse(entityName + " Delete success customer");
    }
}


package com.tecsup.product_service.factory;

import com.tecsup.product_service.dto.MsgResponse;

public class ResponseFactory {
    public MsgResponse createDeleteResponse(String entityName) {
        return new MsgResponse(entityName + " eliminado exitosamente");
    }
    public MsgResponse codigoExistingResponse(String entityName) {
        return new MsgResponse("Duplicate Código : "+entityName);
    }

    public MsgResponse documentNumberNoExistingResponse(String documentNumber) {
        return new MsgResponse("No existe el numero de documento : "+documentNumber);
    }

    public MsgResponse badIdProduct(String id) {
        return new MsgResponse("bad id product input : "+id);
    }
    public MsgResponse notFoundProduct(String id) {
        return new MsgResponse("Not found product: "+id);
    }
    public MsgResponse missingFieldRequerid(){
        return new MsgResponse("Missing Filed Required");
    }
}

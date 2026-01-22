package com.tecsup.financial_origination_service.mapper;


import com.tecsup.financial_origination_service.dto.*;
import com.tecsup.financial_origination_service.entity.CustomerProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CostumerProductMapper {
    CustomerProduct toDomain(CustomerProductEntity entity);
    CustomerProduct toDomain(CustomerProductRequest request);
    CustomerProductEntity toEntity(CustomerProduct domain);
    CustomerProductResponse toResponse(CustomerProduct domain);
}

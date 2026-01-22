package com.dominio.customer_service.mapper;

import com.dominio.customer_service.dto.Customer;
import com.dominio.customer_service.dto.CustomerRequest;
import com.dominio.customer_service.dto.CustomerResponse;
import com.dominio.customer_service.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer domain);

    CustomerResponse toResponse(Customer domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toDomain(CustomerRequest request);
}

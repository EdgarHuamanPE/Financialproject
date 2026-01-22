package com.tecsup.financial_origination_service.mapper;

import com.tecsup.financial_origination_service.dto.CustomerProduct;
import com.tecsup.financial_origination_service.dto.UpdateCustomerProductRequest;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UpdateCustomerProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "contractNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    void updateEntityFromRequest(UpdateCustomerProductRequest request,@MappingTarget CustomerProduct domain);


}



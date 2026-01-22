package com.tecsup.product_service.mapper;

import com.tecsup.product_service.dto.Product;
import com.tecsup.product_service.dto.ProductRequest;
import com.tecsup.product_service.dto.ProductResponse;
import com.tecsup.product_service.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomain(ProductEntity entity);
    Product toDomain(ProductRequest request);
    ProductEntity toEntity(Product domain);
    ProductResponse toResponse(Product domain);
}

package com.tecsup.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UpdateProductRequest {
    private String name;
    private String type;
    private String category;
    private String currency;
    private BigDecimal interestRate;
    private String description;
    private String status;
}

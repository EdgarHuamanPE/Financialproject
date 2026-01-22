package com.tecsup.financial_origination_service.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    private String code;
    private String type;
    private String name;
    private BigDecimal balance;
}

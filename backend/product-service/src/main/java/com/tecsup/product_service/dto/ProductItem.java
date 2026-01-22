package com.tecsup.product_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItem {
    @JsonProperty("tipo de producto")
    private String type;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("saldo")
    private BigDecimal balance;
}

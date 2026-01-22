package com.tecsup.financial_origination_service.dto;

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
public class ProductItemResponse {
    @JsonProperty("código")
    private String code;

    @JsonProperty("tipo de producto")
    private String type;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("saldo")
    private BigDecimal balance;
}

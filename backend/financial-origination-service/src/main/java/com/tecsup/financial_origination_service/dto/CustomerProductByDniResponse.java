package com.tecsup.financial_origination_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"firstName", "lastName", "documentType","documentNumber"})
public class CustomerProductByDniResponse {
    @JsonProperty("nombre")
    private String firstName;
    @JsonProperty("apellido")
    private String lastName;
    @JsonProperty("tipo de documento")
    private String documentType;
    @JsonProperty("numero de documento")
    private String documentNumber;
    @JsonProperty("productos")
    private List<ProductItemResponse> products;
}

package com.dominio.customer_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CustomerRequest {

    @NotNull(message = "documentType is required")
    private String documentType;

    @NotNull(message = "documentNumber is required")
    private String documentNumber;

    @NotNull(message = "firstName is required")
    private String firstName;

    @NotNull(message = "lastName is required")
    private String lastName;

    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "phone is required")
    private String phone;

}
